package com.beautyathome.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.infrastructure.persistence.entity.BookingEntity;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
    // Spring infiere la query SQL automáticamente por el nombre del método
    List<BookingEntity> findByProfessionalId(String professionalId);
}