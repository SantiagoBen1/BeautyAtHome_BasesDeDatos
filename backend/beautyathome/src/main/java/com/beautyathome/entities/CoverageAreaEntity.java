package com.beautyathome.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coverage_areas")
public class CoverageAreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coverage")
    private Integer id;

    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;

    @Column(name = "neighborhood_name", nullable = false, length = 100)
    private String neighborhoodName;

    public CoverageAreaEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getNeighborhoodName() { return neighborhoodName; }
    public void setNeighborhoodName(String neighborhoodName) { this.neighborhoodName = neighborhoodName; }
}
