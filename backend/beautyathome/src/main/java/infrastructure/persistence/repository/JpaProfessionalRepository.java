package infrastructure.persistence.repository;

import infrastructure.persistence.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProfessionalRepository extends JpaRepository<ProfessionalEntity, String> {
}