package infrastructure.persistence.dao.postgres;

import domain.booking.Booking;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.entity.BookingEntity;
import infrastructure.persistence.repository.JpaBookingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresBookingDAO implements BookingDAO {

    private final JpaBookingRepository repository;

    public PostgresBookingDAO(JpaBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking save(Booking entity) {
        if (entity == null) throw new IllegalArgumentException("Booking cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        // Ajustar getters para obtener IDs en lugar de objetos completos temporalmente
        BookingEntity bookingEntity = new BookingEntity(id, 
                entity.getClient().getId(), 
                entity.getProfessional().getId(), 
                entity.getDate(), 
                entity.getState().toString());
                
        BookingEntity saved = repository.save(bookingEntity);
        
        // TODO: Reconstruir el objeto Booking usando Builder (BookingBuilder)
        return null; 
    }

    @Override
    public Booking findById(String id) {
        // TODO: Reconstruir usando el Builder
        return null;
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<Booking> findAll() {
        // TODO: Implementar lista
        return List.of();
    }
}