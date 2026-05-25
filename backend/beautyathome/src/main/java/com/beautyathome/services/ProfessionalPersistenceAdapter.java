package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.professional.Professional;
import com.beautyathome.repositories.ProfessionalRepository;
import com.beautyathome.repositories.JpaProfessionalRepository;
import com.beautyathome.entities.ProfessionalEntity;
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
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Professional> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private ProfessionalEntity toEntity(Professional domain) {
        ProfessionalEntity entity = new ProfessionalEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }

    private Professional toDomain(ProfessionalEntity entity) {
        // Nota: Deberás utilizar tu Factory aquí para instanciar el tipo correcto (ej. HairStylist)
        // Basado en algún campo discriminador de la entidad.
        return null; // Placeholder para que lo adaptes a tu Factory
    }
}