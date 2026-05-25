package com.beautyathome.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class ServiceEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(length = 100)
    private String category;

    @Column(name = "professional_id", length = 36)
    private String professionalId;

    @OneToMany(mappedBy = "service")
    private List<BookingEntity> bookings = new ArrayList<>();

    public ServiceEntity() {}

    public ServiceEntity(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getProfessionalId() { return professionalId; }
    public void setProfessionalId(String professionalId) { this.professionalId = professionalId; }
    public List<BookingEntity> getBookings() { return bookings; }
}