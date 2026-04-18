// Archivo: src/main/java/com/beautyathome/infrastructure/persistence/entity/BookingEntity.java
package com.beautyathome.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    // Relaciones listas para cuando se migren ClientEntity y ProfessionalEntity
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "professional_id", nullable = false)
    private String professionalId;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    // Persistiremos el State Pattern como un simple String en la BD
    @Column(name = "status", nullable = false)
    private String status; 

    public BookingEntity() {}

    // Constructor completo
    public BookingEntity(String id, String clientId, String professionalId, LocalDateTime bookingDate, String status) {
        this.id = id;
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters y Setters...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getProfessionalId() { return professionalId; }
    public void setProfessionalId(String professionalId) { this.professionalId = professionalId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}