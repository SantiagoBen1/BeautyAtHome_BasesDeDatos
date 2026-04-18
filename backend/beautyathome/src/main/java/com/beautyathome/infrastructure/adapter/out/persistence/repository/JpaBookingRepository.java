package com.beautyathome.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.infrastructure.adapter.out.persistence.entity.BookingEntity;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
    // Spring infiere la query SQL automáticamente por el nombre del método
    List<BookingEntity> findByProfessionalId(String professionalId);

    List<BookingEntity> findByClientId(String clientId);
}