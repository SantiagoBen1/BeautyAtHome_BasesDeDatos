// Archivo: src/main/java/com/beautyathome/domain/booking/port/out/BookingRepositoryPort.java
package com.beautyathome.domain.booking.port.out;

import com.beautyathome.domain.booking.Booking;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida. Contrato que la infraestructura debe cumplir
 * para persistir reservas sin que el dominio sepa de SQL o JPA.
 */
public interface BookingRepositoryPort {
    Booking save(Booking booking);
    Optional<Booking> findById(String id);
    List<Booking> findByProfessionalId(String professionalId);
    List<Booking> findAll();
    void delete(String id);
}