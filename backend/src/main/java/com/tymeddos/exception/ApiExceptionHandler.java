package com.tymeddos.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("detail", exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("detail", exception.getMessage()));
    }
}
