package com.beautyathome.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.entities.BookingEntity;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
    List<BookingEntity> findByProfessionalId(String professionalId);
    List<BookingEntity> findByClientId(String clientId);
}