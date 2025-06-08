package com.example.transaction_service.controller.common.advice;

import com.example.transaction_service.controller.common.advice.handler.CommonControllerExceptionHandler;
import com.example.transaction_service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик исключений для всех контроллеров
 */
@ControllerAdvice(annotations = CommonControllerExceptionHandler.class)
public class CommonControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException e) {
        var map = new HashMap<String, String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> authenticationExceptionHandler(AuthenticationException e) {
        return new ResponseEntity<>(Map.of("authentication error", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(Map.of("not found", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> invalidDataExceptionHandler(InvalidDataException e) {
        return new ResponseEntity<>(Map.of("invalid data", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProcessingException.class)
    public ResponseEntity<Map<String, String>> processingExceptionHandler(ProcessingException e) {
        return new ResponseEntity<>(Map.of("processing error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Map<String, String>> transactionExceptionHandler(TransactionException e) {
        return new ResponseEntity<>(Map.of("transaction error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
