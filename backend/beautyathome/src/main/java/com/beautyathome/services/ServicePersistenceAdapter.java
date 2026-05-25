package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.ServiceLeaf;
import com.beautyathome.entities.ServiceEntity;
import com.beautyathome.entities.CategoryEntity;
import com.beautyathome.repositories.ServiceRepository;
import com.beautyathome.repositories.JpaServiceRepository;

@Component
public class ServicePersistenceAdapter implements ServiceRepository {

    private final JpaServiceRepository jpaRepository;

    public ServicePersistenceAdapter(JpaServiceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ServiceComponent save(ServiceComponent service) {
        ServiceEntity entity = toEntity(service);
        return toDomain(jpaRepository.save(entity));
    }
    
    @Override
    public ServiceComponent saveForProfessional(String professionalId, ServiceComponent service) {
        return save(service);
    }

    @Override
    public Optional<ServiceComponent> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ServiceComponent> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceComponent> findByProfessionalId(String professionalId) {
        return findAll();
    }

    @Override
    public void delete(String id) {
        try {
            jpaRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {}
    }

    private ServiceEntity toEntity(ServiceComponent domain) {
        ServiceEntity entity = new ServiceEntity();
        
        entity.setName(domain.getName());
        entity.setBasePrice(0.0); // No getter in component
        entity.setEstimatedDuration(60);
        
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        entity.setCategory(category);
        
        return entity;
    }

    private ServiceComponent toDomain(ServiceEntity entity) {
        return new ServiceLeaf(
            String.valueOf(entity.getId()),
            entity.getName(),
            entity.getBasePrice(),
            entity.getEstimatedDuration(),
            new ArrayList<>()
        );
    }
}
