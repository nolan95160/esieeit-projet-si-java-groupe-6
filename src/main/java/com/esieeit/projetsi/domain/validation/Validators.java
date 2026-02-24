package com.esieeit.projetsi.domain.validation;

import com.esieeit.projetsi.domain.exception.ValidationException;

import java.util.regex.Pattern;

/**
 * Utility class for domain validations.
 * Centralizes validation logic to avoid duplication.
 */
public final class Validators {

    private Validators() {
        // Prevent instantiation
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public static void notBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }

    public static void maxLength(String value, int max, String fieldName) {
        if (value != null && value.length() > max) {
            throw new ValidationException(fieldName + " must be <= " + max + " characters.");
        }
    }

    public static void positive(Long value, String fieldName) {
        if (value == null || value <= 0) {
            throw new ValidationException(fieldName + " must be positive.");
        }
    }

    public static void email(String value, String fieldName) {
        notBlank(value, fieldName);
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new ValidationException(fieldName + " must be a valid email.");
        }
    }
}