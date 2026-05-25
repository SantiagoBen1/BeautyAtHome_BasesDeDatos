package com.beautyathome.services;

import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.repositories.ServiceRepository;
import com.beautyathome.repositories.JpaServiceRepository;
import com.beautyathome.entities.ServiceEntity;
import com.beautyathome.repositories.ServiceRepository;
import com.beautyathome.repositories.JpaServiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<ServiceComponent> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<ServiceComponent> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        jpaRepository.deleteById(id);
    }

    private ServiceEntity toEntity(ServiceComponent domain) {
        ServiceEntity entity = new ServiceEntity();
        entity.setId(domain.getName());
        return entity;
    }

    private ServiceComponent toDomain(ServiceEntity entity) {
        // Reconstruye el objeto puro usando tus Constructores/Builders de servicio
        return null; 
    }
    @Override
    public List<ServiceComponent> findByProfessionalId(String professionalId) {
        // Implementación requerida por ServiceRepository
        return java.util.Collections.emptyList();
    }

    @Override
    public ServiceComponent saveForProfessional(String professionalId, ServiceComponent service) {
        // Implementación requerida por ServiceRepository
        return save(service);
    }
}
