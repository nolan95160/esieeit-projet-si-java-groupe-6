package com.esieeit.projetsi.domain.exception;

/**
 * Base exception for the domain layer.
 * All domain-related exceptions should extend this class.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}