// Archivo: src/main/java/com/beautyathome/infrastructure/persistence/adapter/BookingPersistenceAdapter.java
package com.beautyathome.infrastructure.persistence.adapter;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.infrastructure.persistence.entity.BookingEntity;
import com.beautyathome.infrastructure.persistence.repository.JpaBookingRepository;
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

    // --- MAPPERS INTERNOS ---
    // En el futuro, considera usar MapStruct para automatizar esto.
    
    private BookingEntity toEntity(Booking domain) {
        // Asumiendo que tu clase Booking (Dominio) expone estos datos
        return new BookingEntity(
            domain.getId(),
            domain.getClientId(),
            domain.getProfessionalId(),
            domain.getDate(),
            domain.getState().toString() // Extrae el estado del patrón State a un String
        );
    }

    private Booking toDomain(BookingEntity entity) {
        // Aquí reconstruyes el objeto de dominio complejo a partir de los datos crudos de BD.
        // Utiliza tu BookingBuilder actual para ensamblarlo.
        return new Booking.Builder()
            .id(entity.getId())
            .clientId(entity.getClientId())
            .professionalId(entity.getProfessionalId())
            .date(entity.getBookingDate())
            .status(entity.getStatus()) // Debes rehidratar el patrón State aquí
            .build();
    }
}