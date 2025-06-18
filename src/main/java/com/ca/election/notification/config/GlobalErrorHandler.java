package com.ca.election.notification.config;
import com.ca.election.notification.exception.CAException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(-2) // Ensures this handler runs before the default one
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalErrorHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = new HashMap<>();

        body.put("type", "about:blank");
        body.put("instance", exchange.getRequest().getPath().toString());
        body.put("timestamp", OffsetDateTime.now(ZoneOffset.UTC)); // Updated for Zulu format

        if (ex instanceof WebExchangeBindException bindEx) {
            status = HttpStatus.BAD_REQUEST;
            body.put("title", "Validation Failed");
            body.put("status", status.value());

            Map<String, String> errors = new HashMap<>();
            bindEx.getFieldErrors().forEach(fieldError ->
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage())
            );
            body.put("errors", errors);

        } else if (ex instanceof IllegalArgumentException || ex instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            status = HttpStatus.BAD_REQUEST;
            body.put("title", "Invalid Request");
            body.put("status", status.value());
            body.put("detail", "Invalid value provided for an enum field. Please use one of: EMAIL, TEAMS, CALENDAR.");

        } else if (ex instanceof CAException caEx) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body.put("title", "Corporate Action Error");
            body.put("status", status.value());
            body.put("detail", caEx.getMessage());

        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            body.put("title", "Unexpected Error");
            body.put("status", status.value());
            body.put("detail", ex.getMessage() != null ? ex.getMessage() : "An internal error occurred");
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
            return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(bytes)));
        } catch (JsonProcessingException e) {
            byte[] fallback = ("{\"title\": \"Internal Error\", \"detail\": \"Failed to serialize error response\"}")
                    .getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(fallback)));
        }
    }
}
