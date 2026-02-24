package com.esieeit.projetsi.domain.exception;

/**
 * Thrown when a business rule is violated
 * (ownership, permissions, invalid workflow, etc.).
 */
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String message) {
        super(message);
    }
}