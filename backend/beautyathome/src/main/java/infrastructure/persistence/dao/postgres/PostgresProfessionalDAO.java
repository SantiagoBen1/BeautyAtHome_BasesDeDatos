package infrastructure.persistence.dao.postgres;

import domain.professional.Professional;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.entity.ProfessionalEntity;
import infrastructure.persistence.repository.JpaProfessionalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostgresProfessionalDAO implements ProfessionalDAO {

    private final JpaProfessionalRepository repository;

    public PostgresProfessionalDAO(JpaProfessionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Professional save(Professional entity) {
        if (entity == null) throw new IllegalArgumentException("Professional cannot be null");
        
        String id = (entity.getId() == null || entity.getId().isBlank()) 
                ? UUID.randomUUID().toString() 
                : entity.getId();
                
        ProfessionalEntity profEntity = new ProfessionalEntity(id, entity.getName());
        ProfessionalEntity saved = repository.save(profEntity);
        
        // Asumiendo un constructor básico en tu dominio. Ajustar según clase Professional.
        return new Professional(saved.getId(), saved.getName());
    }

    @Override
    public Professional findById(String id) {
        return repository.findById(id)
                .map(e -> new Professional(e.getId(), e.getName()))
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        if (id != null) repository.deleteById(id);
    }

    @Override
    public List<Professional> findAll() {
        return repository.findAll().stream()
                .map(e -> new Professional(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }
}