package com.example.transaction_manager_service.exception;

public class RejectedException extends RuntimeException {
    public RejectedException(String message) {
        super(message);
    }
}
