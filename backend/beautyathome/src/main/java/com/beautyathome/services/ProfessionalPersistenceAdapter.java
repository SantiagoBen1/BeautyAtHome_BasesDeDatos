package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.HairStylist;
import com.beautyathome.entities.ProfessionalEntity;
import com.beautyathome.entities.BrandEntity;
import com.beautyathome.repositories.ProfessionalRepository;
import com.beautyathome.repositories.JpaProfessionalRepository;

@Component
@Transactional(readOnly = true)
public class ProfessionalPersistenceAdapter implements ProfessionalRepository {

    private final JpaProfessionalRepository jpaRepository;

    public ProfessionalPersistenceAdapter(JpaProfessionalRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
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
    @Transactional
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
        entity.setPhotoUrl(domain.getPhotoUrl());
        
        BrandEntity brand = new BrandEntity();
        brand.setId(1);
        entity.setBrand(brand);
        
        return entity;
    }

    private Professional toDomain(ProfessionalEntity entity) {
        com.beautyathome.domain.professional.Brand domainBrand = null;
        if (entity.getBrand() != null) {
            domainBrand = new com.beautyathome.domain.professional.Brand(
                entity.getBrand().getName(),
                entity.getBrand().getLogoUrl()
            );
        }
        
        List<com.beautyathome.domain.service.ServiceComponent> domainServices = new java.util.ArrayList<>();
        if (entity.getServices() != null) {
            for (com.beautyathome.entities.ServiceEntity s : entity.getServices()) {
                domainServices.add(new com.beautyathome.domain.service.ServiceLeaf(
                    s.getName(),
                    s.getDescription(),
                    s.getBasePrice(),
                    s.getEstimatedDuration(),
                    new java.util.ArrayList<>()
                ));
            }
        }
        
        List<com.beautyathome.domain.professional.CoverageArea> domainCoverage = new java.util.ArrayList<>();
        if (entity.getCoverageAreas() != null) {
            for (com.beautyathome.entities.CoverageAreaEntity c : entity.getCoverageAreas()) {
                domainCoverage.add(new com.beautyathome.domain.professional.CoverageArea(
                    c.getNeighborhoodName()
                ));
            }
        }
        
        return new HairStylist(
            String.valueOf(entity.getId()),
            entity.getUserName(),
            entity.getPhotoUrl(), // photoUrl
            entity.getBioExperience(), // experienceSummary
            domainCoverage, // coverageAreas
            domainBrand, // brand
            domainServices // services
        );
    }
}