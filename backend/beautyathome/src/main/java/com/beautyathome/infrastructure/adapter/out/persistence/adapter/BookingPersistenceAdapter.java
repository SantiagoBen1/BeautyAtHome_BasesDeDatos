package com.beautyathome.infrastructure.adapter.out.persistence.adapter; // Paquete corregido

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
// Imports actualizados a la nueva estructura de carpetas
import com.beautyathome.infrastructure.adapter.out.persistence.entity.BookingEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaBookingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingPersistenceAdapter implements BookingRepositoryPort {

    private final JpaBookingRepository jpaRepository;

    public BookingPersistenceAdapter(JpaBookingRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Booking save(Booking booking) {
        BookingEntity entity = toEntity(booking);
        BookingEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Booking> findByProfessionalId(String professionalId) {
        return jpaRepository.findByProfessionalId(professionalId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private BookingEntity toEntity(Booking domain) {
        return new BookingEntity(
            domain.getId(),
            domain.getClientId(),
            domain.getProfessionalId(),
            domain.getDate(),
            domain.getState() != null ? domain.getState().toString() : "PENDING" 
        );
    }

    private Booking toDomain(BookingEntity entity) {
        return new Booking.Builder()
            .id(entity.getId())
            .clientId(entity.getClientId())
            .professionalId(entity.getProfessionalId())
            .date(entity.getBookingDate())
            // .status(entity.getStatus()) // Descomenta y adapta segÃºn tu BookingBuilder
            .build();
    }
}