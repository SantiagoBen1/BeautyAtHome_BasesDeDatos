package com.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.entities.ClientEntity;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, Integer> {
}