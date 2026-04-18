package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.BookingBuilder;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.BookingEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaBookingRepository;

@Component
public class BookingPersistenceAdapter implements BookingRepositoryPort {

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
        // Necesitarás agregar findByClientId en JpaBookingRepository
        // return jpaRepository.findByClientId(clientId).stream().map(this::toDomain).collect(Collectors.toList());
        return List.of(); 
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
        BookingEntity entity = new BookingEntity();
        entity.setId(domain.getId());
        entity.setClientId(domain.getClientId());
        entity.setProfessionalId(domain.getProfessionalId());
        entity.setBookingDate(domain.getDateTime());
        return entity;
    }

    private Booking toDomain(BookingEntity entity) {
        return new BookingBuilder()
            .withClient(entity.getClientId())
            .withProfessional(entity.getProfessionalId())
            .withDate(entity.getBookingDate())
            .build();
    }
}