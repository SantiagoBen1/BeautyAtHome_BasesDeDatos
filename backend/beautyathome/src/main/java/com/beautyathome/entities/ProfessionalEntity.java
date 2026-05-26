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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que mapea la tabla 'professionals' en PostgreSQL.
 * 
 * DESCRIPCIÓN DE LA BASE DE DATOS:
 * Tabla que almacena el catálogo de trabajadores independientes.
 * - Restricciones: 'user_name' tiene la restricción UNIQUE a nivel de BD para
 * evitar duplicados.
 * - Relaciones bidireccionales One-to-Many con 'bookings' (reservas).
 * - Relaciones Many-to-Many implementadas con @JoinTable:
 * 1. 'professional_service': Qué servicios es capaz de realizar.
 * 2. 'professional_coverage': En qué códigos postales (zonas) está dispuesto a
 * trabajar.
 */
@Entity
@Table(name = "professionals")
public class ProfessionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesional")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brand", nullable = false)
    private BrandEntity brand;

    @Column(name = "user_name", nullable = false, unique = true, length = 80)
    private String userName;

    @Column(name = "bio_experience", length = 300)
    private String bioExperience;

    @Column(name = "speciality", length = 100)
    private String speciality;

    @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "activo";

    @OneToMany(mappedBy = "professional")
    private List<BookingEntity> bookings = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "professional_service", joinColumns = @JoinColumn(name = "id_profesional"), inverseJoinColumns = @JoinColumn(name = "id_service"))
    private List<ServiceEntity> services = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "professional_coverage", joinColumns = @JoinColumn(name = "id_profesional"), inverseJoinColumns = @JoinColumn(name = "id_coverage"))
    private List<CoverageAreaEntity> coverageAreas = new ArrayList<>();

    public ProfessionalEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBioExperience() {
        return bioExperience;
    }

    public void setBioExperience(String bioExperience) {
        this.bioExperience = bioExperience;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    public List<CoverageAreaEntity> getCoverageAreas() {
        return coverageAreas;
    }

    public void setCoverageAreas(List<CoverageAreaEntity> coverageAreas) {
        this.coverageAreas = coverageAreas;
    }
}