package com.beautyathome.exceptions;

/**
 * Excepción lanzada cuando una validación de negocio falla.
 */
public class BusinessValidationException extends RuntimeException {

    public BusinessValidationException(String message) {
        super(message);
    }
}
