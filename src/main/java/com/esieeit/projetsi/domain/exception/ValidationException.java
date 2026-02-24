package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when a validation rule is violated
 * (null, blank, invalid format, etc.).
 */
public class ValidationException extends DomainException {

    public ValidationException(String message) {
        super(message);
    }
}