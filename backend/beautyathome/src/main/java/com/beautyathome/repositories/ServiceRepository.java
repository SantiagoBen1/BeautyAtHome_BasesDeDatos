package com.beautyathome.repositories;

import java.util.List;
import java.util.Optional;
import com.beautyathome.domain.service.ServiceComponent;

public interface ServiceRepository {
    ServiceComponent save(ServiceComponent service);
    Optional<ServiceComponent> findById(String id);
    List<ServiceComponent> findAll();
    void delete(String id);
    List<ServiceComponent> findByProfessionalId(String professionalId);
    ServiceComponent saveForProfessional(String professionalId, ServiceComponent service);
}
