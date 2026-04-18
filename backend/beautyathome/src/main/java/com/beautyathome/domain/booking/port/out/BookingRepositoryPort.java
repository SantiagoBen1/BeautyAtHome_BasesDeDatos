package com.beautyathome.domain.booking.port.out;

import com.beautyathome.domain.booking.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingRepositoryPort {
    Booking save(Booking booking);
    Optional<Booking> findById(String id);
    List<Booking> findByProfessionalId(String professionalId);
    List<Booking> findAll();
    void delete(String id);
}