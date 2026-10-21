
package com.medvault;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(
                error.getField(),
                error.getDefaultMessage() == null
                    ? "Invalid value"
                    : error.getDefaultMessage()
            );
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(
            IllegalArgumentException ex) {

        Map<String, String> error = new LinkedHashMap<>();

        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
        DataIntegrityViolationException ex) {

    Map<String, String> error = new LinkedHashMap<>();
    error.put("message", "A record with the provided details already exists.");

    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(error);
}
}
