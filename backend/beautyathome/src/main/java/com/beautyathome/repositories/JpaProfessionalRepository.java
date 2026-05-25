package com.beautyathome.repositories;

import com.beautyathome.entities.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, String> {
}