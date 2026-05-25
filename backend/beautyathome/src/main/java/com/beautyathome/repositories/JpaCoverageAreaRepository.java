package com.beautyathome.repositories;

import com.beautyathome.entities.CoverageAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCoverageAreaRepository extends JpaRepository<CoverageAreaEntity, Integer> {
    Optional<CoverageAreaEntity> findByNeighborhoodNameIgnoreCase(String name);
}
