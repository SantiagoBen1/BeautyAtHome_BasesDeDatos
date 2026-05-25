package com.beautyathome.repositories;

import com.beautyathome.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> findByProfessionals_Id(Integer professionalId);

    @Modifying
    @Query(value = "INSERT INTO professional_service (id_profesional, id_service) VALUES (:professionalId, :serviceId)", nativeQuery = true)
    void linkServiceToProfessional(@Param("professionalId") Integer professionalId, @Param("serviceId") Integer serviceId);
}