package com.beautyathome.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {
    
    @Id
    private String id;
    private String name;
    private String email;

    public ClientEntity() {}

    public ClientEntity(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}