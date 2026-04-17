package infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import infrastructure.persistence.entity.ClientEntity;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, String> {
}