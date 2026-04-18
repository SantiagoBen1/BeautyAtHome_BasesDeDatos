package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import com.beautyathome.domain.professional.Professional;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.ProfessionalEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaProfessionalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProfessionalPersistenceAdapter implements ProfessionalRepositoryPort {

    private final JpaProfessionalRepository repository;

    public ProfessionalPersistenceAdapter(JpaProfessionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Professional save(Professional entity) {
        if (entity == null) throw new IllegalArgumentException("Professional cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        ProfessionalEntity profEntity = new ProfessionalEntity(id, entity.getName());
        ProfessionalEntity saved = repository.save(profEntity);
        
        // Asumiendo un constructor bÃ¡sico en tu dominio. Ajustar segÃºn clase Professional.
        return new Professional(saved.getId(), saved.getName());
    }

    @Override
    public Professional findById(String id) {
        return repository.findById(id)
                .map(e -> new Professional(e.getId(), e.getName()))
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<Professional> findAll() {
        return repository.findAll().stream()
                .map(e -> new Professional(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }
}