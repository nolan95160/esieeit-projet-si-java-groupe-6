package com.esieeit.projetsi.api.error;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.InvalidDataException;
import com.esieeit.projetsi.domain.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {

        List<FieldErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
            .map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage()))
            .collect(Collectors.toList());

        ErrorResponse body = new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            "La requête est invalide",
            req.getRequestURI(),
            details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {

        ErrorResponse body = new ErrorResponse(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage(),
            req.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException ex, HttpServletRequest req) {

        ErrorResponse body = new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "INVALID_DATA",
            ex.getMessage(),
            req.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(BusinessRuleException ex, HttpServletRequest req) {

        ErrorResponse body = new ErrorResponse(
            Instant.now(),
            HttpStatus.CONFLICT.value(),
            "BUSINESS_RULE_VIOLATION",
            ex.getMessage(),
            req.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest req) {

        ErrorResponse body = new ErrorResponse(
            Instant.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_ERROR",
            "Une erreur inattendue est survenue",
            req.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
