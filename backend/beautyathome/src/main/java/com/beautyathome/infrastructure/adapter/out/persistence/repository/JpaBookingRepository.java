package com.beautyathome.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.infrastructure.adapter.out.persistence.entity.BookingEntity;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
    // Spring infiere la query SQL automÃ¡ticamente por el nombre del mÃ©todo
    List<BookingEntity> findByProfessionalId(String professionalId);
}