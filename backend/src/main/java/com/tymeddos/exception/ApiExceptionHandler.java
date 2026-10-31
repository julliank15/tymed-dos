package com.tymeddos.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return error(HttpStatus.BAD_REQUEST, "Invalid value for '" + exception.getName() + "'");
    }

    // @Min/@Max etc. on @RequestParam: Spring 6.1+ raises HandlerMethodValidationException
    // (or ConstraintViolationException with class-level @Validated). Without these handlers they become 500s.
    @ExceptionHandler({
            HandlerMethodValidationException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<Map<String, String>> error(HttpStatus status, String detail) {
        return ResponseEntity.status(status)
                .body(Map.of("detail", detail == null ? status.getReasonPhrase() : detail));
    }
}
