package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.BookingBuilder;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.repositories.JpaBookingRepository;
import com.beautyathome.entities.BookingEntity;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.repositories.JpaBookingRepository;

@Component
public class BookingPersistenceAdapter implements BookingRepository {

    private final JpaBookingRepository jpaRepository;

    public BookingPersistenceAdapter(JpaBookingRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Booking save(Booking booking) {
        BookingEntity entity = toEntity(booking);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Booking> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Booking> findByProfessionalId(String professionalId) {
        return jpaRepository.findByProfessionalId(professionalId).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByClientId(String clientId) {
        // Implementación corregida usando el nuevo método del repositorio JPA
        return jpaRepository.findByClientId(clientId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private BookingEntity toEntity(Booking domain) {
        String status = domain.getState() != null ? domain.getState().getClass().getSimpleName() : "PENDING";
        
        return new BookingEntity(
            domain.getId(),
            domain.getClientId(),
            domain.getProfessionalId(),
            domain.getDateTime(),
            status
        );
    }

    private Booking toDomain(BookingEntity entity) {
        return new BookingBuilder()
            .withId(entity.getId())
            .withClient(entity.getClientId())
            .withProfessional(entity.getProfessionalId())
            .withDate(entity.getBookingDate())
            // .withStatus(entity.getStatus()) // Necesario en el Builder para reconstruir el State
            .build();
    }
}