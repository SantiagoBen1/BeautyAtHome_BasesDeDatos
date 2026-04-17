package infrastructure.persistence.repository;

import infrastructure.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, String> {
}