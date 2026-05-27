package com.beautyathome.exceptions;

/**
 * Excepción lanzada cuando existe un conflicto de horario al crear una reserva.
 */
public class BookingConflictException extends RuntimeException {

    public BookingConflictException(String message) {
        super(message);
    }

    public BookingConflictException(String professionalId, String dateTime) {
        super(String.format("Conflicto de horario para el profesional '%s' en la fecha '%s'", professionalId, dateTime));
    }
}
