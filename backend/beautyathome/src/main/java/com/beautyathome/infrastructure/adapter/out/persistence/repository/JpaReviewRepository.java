package infrastructure.persistence.repository;

import infrastructure.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends JpaRepository<ReviewEntity, String> {
}