package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.HairStylist;
import com.beautyathome.entities.ProfessionalEntity;
import com.beautyathome.entities.BrandEntity;
import com.beautyathome.repositories.ProfessionalRepository;
import com.beautyathome.repositories.JpaProfessionalRepository;

@Component
public class ProfessionalPersistenceAdapter implements ProfessionalRepository {

    private final JpaProfessionalRepository jpaRepository;

    public ProfessionalPersistenceAdapter(JpaProfessionalRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Professional save(Professional professional) {
        ProfessionalEntity entity = toEntity(professional);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Professional> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Professional> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        try {
            jpaRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {}
    }

    private ProfessionalEntity toEntity(Professional domain) {
        ProfessionalEntity entity = new ProfessionalEntity();
        if (domain.getId() != null && !domain.getId().isEmpty()) {
            try {
                entity.setId(Integer.parseInt(domain.getId()));
            } catch (NumberFormatException e) {}
        }
        
        entity.setUserName(domain.getName() != null ? domain.getName().replaceAll("\\s+", "_").toLowerCase() : "unknown");
        entity.setPhone("0000000000"); // Dummy
        entity.setRating(5.0);
        
        BrandEntity brand = new BrandEntity();
        brand.setId(1);
        entity.setBrand(brand);
        
        return entity;
    }

    private Professional toDomain(ProfessionalEntity entity) {
        return new HairStylist(
            String.valueOf(entity.getId()),
            entity.getUserName(),
            "dummy@email.com",
            entity.getPhone() != null ? entity.getPhone() : "0000000000",
            new java.util.ArrayList<>(),
            null,
            new java.util.ArrayList<>()
        );
    }
}