package com.beautyathome.repositories;

import com.beautyathome.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, Integer> {
}