package com.ca.election.notification.exception;

public class CAException extends  Exception{

    public CAException() {
    }

    public CAException(String message) {
        super(message);
    }

    public CAException(String message, Throwable cause) {
        super(message, cause);
    }

    public CAException(Throwable cause) {
        super(cause);
    }

    public CAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
