package com.beautyathome.infrastructure.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beautyathome.infrastructure.adapter.out.persistence.entity.ClientEntity;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, String> {
}