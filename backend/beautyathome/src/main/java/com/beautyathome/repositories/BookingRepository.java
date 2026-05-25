package com.beautyathome.repositories;

import java.util.List;
import java.util.Optional;
import com.beautyathome.domain.booking.Booking;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(String id);
    List<Booking> findByProfessionalId(String professionalId);
    List<Booking> findByClientId(String clientId);
    List<Booking> findAll();
    void delete(String id);
}
