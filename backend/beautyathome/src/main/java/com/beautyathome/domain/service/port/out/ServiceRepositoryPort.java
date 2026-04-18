package com.beautyathome.domain.service.port.out;

import java.util.List;
import java.util.Optional;

import com.beautyathome.domain.service.ServiceComponent;

public interface ServiceRepositoryPort {
    ServiceComponent save(ServiceComponent service);
    Optional<ServiceComponent> findById(String id);
    List<ServiceComponent> findAll();
    void delete(String id);
}