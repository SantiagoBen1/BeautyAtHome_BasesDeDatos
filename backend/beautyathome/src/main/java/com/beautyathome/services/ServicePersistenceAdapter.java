package com.beautyathome.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.ServiceLeaf;
import com.beautyathome.entities.ServiceEntity;
import com.beautyathome.entities.CategoryEntity;
import com.beautyathome.repositories.ServiceRepository;
import com.beautyathome.repositories.JpaServiceRepository;

@Component
@Transactional(readOnly = true)
public class ServicePersistenceAdapter implements ServiceRepository {

    private final JpaServiceRepository jpaRepository;

    public ServicePersistenceAdapter(JpaServiceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public ServiceComponent save(ServiceComponent service) {
        ServiceEntity entity = toEntity(service);
        return toDomain(jpaRepository.save(entity));
    }
    
    @Override
    @Transactional
    @SuppressWarnings("null")
    public ServiceComponent saveForProfessional(String professionalId, ServiceComponent service) {
        ServiceEntity entity = toEntity(service);
        entity = jpaRepository.save(entity);
        try {
            jpaRepository.linkServiceToProfessional(Integer.parseInt(professionalId), entity.getId());
        } catch (NumberFormatException e) {}
        return toDomain(entity);
    }

    @Override
    public Optional<ServiceComponent> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return jpaRepository.findFirstByNameIgnoreCase(id).map(this::toDomain);
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
        try {
            return jpaRepository.findByProfessionals_Id(Integer.parseInt(professionalId)).stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
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
        entity.setDescription(domain.getDescription());
        entity.setBasePrice(domain.getPrice() > 0 ? domain.getPrice() : 0.0);
        entity.setEstimatedDuration(domain.getDurationMin() > 0 ? domain.getDurationMin() : 60);
        
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        entity.setCategory(category);
        
        return entity;
    }

    private ServiceComponent toDomain(ServiceEntity entity) {
        return new ServiceLeaf(
            entity.getName(),
            entity.getDescription(),
            entity.getBasePrice() != null ? entity.getBasePrice() : 0.0,
            entity.getEstimatedDuration() != null ? entity.getEstimatedDuration() : 60,
            new ArrayList<>()
        );
    }
}
