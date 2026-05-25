package com.beautyathome.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    
    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Column(name = "client_id", nullable = false, length = 36)
    private String clientId;

    @Column(name = "professional_id", nullable = false, length = 36)
    private String professionalId;

    @Column(name = "service_id", length = 36)
    private String serviceId;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "status", nullable = false, length = 50)
    private String status; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProfessionalEntity professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ServiceEntity service;

    protected BookingEntity() {
        // Constructor vacío requerido por Hibernate
    }

    public BookingEntity(String id, String clientId, String professionalId, LocalDateTime bookingDate, String status) {
        this.id = id;
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    
    public String getProfessionalId() { return professionalId; }
    public void setProfessionalId(String professionalId) { this.professionalId = professionalId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ClientEntity getClient() { return client; }
    public ProfessionalEntity getProfessional() { return professional; }
    public ServiceEntity getService() { return service; }
}