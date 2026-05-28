package com.beautyathome.repositories;

import com.beautyathome.entities.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, Integer> {
    @Query(value = "SELECT * FROM professionals WHERE rating > (SELECT AVG(rating) FROM professionals)", nativeQuery = true)
    List<ProfessionalEntity> findProfessionalsAboveAverageRating();
}