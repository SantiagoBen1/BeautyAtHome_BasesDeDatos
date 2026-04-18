package com.beautyathome.domain.professional.port.out;

import java.util.List;
import java.util.Optional;

import com.beautyathome.domain.professional.Professional;

public interface ProfessionalRepositoryPort {
    Professional save(Professional professional);
    Optional<Professional> findById(String id);
    List<Professional> findAll();
    void delete(String id);
}