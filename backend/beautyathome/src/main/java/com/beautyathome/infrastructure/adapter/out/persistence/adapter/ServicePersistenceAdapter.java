package com.beautyathome.infrastructure.adapter.out.persistence.adapter;

import com.beautyathome.domain.service.ServiceComponent;
import infrastructure.persistence.dao.ServiceDAO;
import com.beautyathome.infrastructure.adapter.out.persistence.entity.ServiceEntity;
import com.beautyathome.infrastructure.adapter.out.persistence.repository.JpaServiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresServiceDAO implements ServiceDAO {

    private final JpaServiceRepository repository;

    public PostgresServiceDAO(JpaServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceComponent save(ServiceComponent entity) {
        if (entity == null) throw new IllegalArgumentException("Service cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        // Mapeo inicial (ajustar getters segÃºn patrÃ³n Composite)
        ServiceEntity serviceEntity = new ServiceEntity(id, entity.getName(), entity.getPrice());
        ServiceEntity saved = repository.save(serviceEntity);
        
        // TODO: Reconstruir tu ServiceComponent / ServiceLeaf a partir de la entidad
        return null; // Cambiar por la instanciaciÃ³n del dominio
    }

    @Override
    public ServiceComponent findById(String id) {
        // TODO: Implementar reconstrucciÃ³n del dominio
        return null; 
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<ServiceComponent> findAll() {
        // TODO: Implementar reconstrucciÃ³n de la lista del dominio
        return List.of();
    }
}