# Use Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set work directory inside container
WORKDIR /app

# Copy pom and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ------------------------

# Use a lightweight JDK 21 image to run the app
FROM eclipse-temurin:21-jdk-jammy

# Set work directory
WORKDIR /app

# Copy built jar from builder image
COPY --from=builder /app/target/notification-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
# Use Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set work directory inside container
WORKDIR /app

# Copy pom and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ------------------------

# Use a lightweight JDK 21 image to run the app
FROM eclipse-temurin:21-jdk-jammy

# Set work directory
WORKDIR /app

# Copy built jar from builder image
COPY --from=builder /app/target/notification-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
