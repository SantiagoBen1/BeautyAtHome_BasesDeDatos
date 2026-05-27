package com.beautyathome.repositories;

import java.util.List;
import java.util.Optional;
import com.beautyathome.domain.professional.Professional;

public interface ProfessionalRepository {
    Professional save(Professional professional);
    Optional<Professional> findById(String id);
    List<Professional> findAll();
    void delete(String id);
}
