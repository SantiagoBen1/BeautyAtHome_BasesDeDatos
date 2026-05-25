package com.beautyathome.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity {
    
    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String type;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "experience_summary", length = 1000)
    private String experienceSummary;

    @Column(name = "brand_name", length = 100)
    private String brandName;

    @Column(name = "brand_logo_url", length = 500)
    private String brandLogoUrl;

    @OneToMany(mappedBy = "professional")
    private List<BookingEntity> bookings = new ArrayList<>();

    public ProfessionalEntity() {}

    public ProfessionalEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public String getExperienceSummary() { return experienceSummary; }
    public void setExperienceSummary(String experienceSummary) { this.experienceSummary = experienceSummary; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public String getBrandLogoUrl() { return brandLogoUrl; }
    public void setBrandLogoUrl(String brandLogoUrl) { this.brandLogoUrl = brandLogoUrl; }
    public List<BookingEntity> getBookings() { return bookings; }
}