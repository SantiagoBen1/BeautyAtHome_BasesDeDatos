package infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class ServiceEntity {
    
    @Id
    private String id;
    private String name;
    private Double price;
    // TODO: Añadir relaciones (ServiceComposite, ServiceLeaf) según el diagrama ER

    public ServiceEntity() {}

    public ServiceEntity(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}