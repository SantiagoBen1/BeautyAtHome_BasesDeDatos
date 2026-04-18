package infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity {
    
    @Id
    private String id;
    private String name;
    // TODO: Añadir más campos (especialidad, etc.) cuando llegue el diagrama ER

    public ProfessionalEntity() {}

    public ProfessionalEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}