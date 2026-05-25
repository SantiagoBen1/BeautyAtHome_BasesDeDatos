package com.beautyathome.repositories;

import com.beautyathome.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> findByProfessionals_Id(Integer professionalId);
}