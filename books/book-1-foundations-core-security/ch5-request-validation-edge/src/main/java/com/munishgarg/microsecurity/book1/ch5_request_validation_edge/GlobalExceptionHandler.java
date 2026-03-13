package com.munishgarg.microsecurity.book1.ch5_request_validation_edge;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * SECURE ERROR HANDLING:
     * When @Valid fails, Spring throws MethodArgumentNotValidException.
     * If unhandled, this might leak internal stack traces or verbose framework errors to the client.
     * We intercept it here and return a sanitized, consistent 400 Bad Request structure.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Extract only the specific field error messages defined in the DTO annotations
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // We do *not* include the ex.getMessage() full stack trace
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
