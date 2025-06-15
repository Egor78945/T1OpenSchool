package com.example.blocking_service.exception;

public class ProcessingException extends RuntimeException {
    public ProcessingException(String message) {
        super(message);
    }
}
