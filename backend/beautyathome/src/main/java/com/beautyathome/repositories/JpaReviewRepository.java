package com.beautyathome.repositories;

import com.beautyathome.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findByBooking_Professional_Id(Integer professionalId);
}