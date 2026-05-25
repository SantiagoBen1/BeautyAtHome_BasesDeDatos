package com.beautyathome.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoryEntity category;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(name = "estimated_duration", nullable = false)
    private Integer estimatedDuration;

    @ManyToMany(mappedBy = "services")
    private List<BookingEntity> bookings = new ArrayList<>();

    @ManyToMany(mappedBy = "services")
    private List<ProfessionalEntity> professionals = new ArrayList<>();

    public ServiceEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<ProfessionalEntity> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<ProfessionalEntity> professionals) {
        this.professionals = professionals;
    }
}