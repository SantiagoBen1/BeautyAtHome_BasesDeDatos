package infrastructure.persistence.repository;

import infrastructure.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaServiceRepository extends JpaRepository<ServiceEntity, String> {
}