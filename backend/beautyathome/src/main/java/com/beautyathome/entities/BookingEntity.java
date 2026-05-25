package com.beautyathome.entities;

import java.time.LocalDateTime;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_booking")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profesional", nullable = false)
    private ProfessionalEntity professional;

    @ManyToMany
    @JoinTable(
        name = "booking_service",
        joinColumns = @JoinColumn(name = "id_booking"),
        inverseJoinColumns = @JoinColumn(name = "id_service")
    )
    private List<ServiceEntity> services = new ArrayList<>();

    @Column(name = "datetime_start", nullable = false)
    private LocalDateTime datetimeStart;

    @Column(name = "datetime_end", nullable = false)
    private LocalDateTime datetimeEnd;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private Double totalPrice;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "pendiente";

    @OneToOne(mappedBy = "booking")
    private ReviewEntity review;

    public BookingEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public ClientEntity getClient() { return client; }
    public void setClient(ClientEntity client) { this.client = client; }
    public ProfessionalEntity getProfessional() { return professional; }
    public void setProfessional(ProfessionalEntity professional) { this.professional = professional; }
    public List<ServiceEntity> getServices() { return services; }
    public void setServices(List<ServiceEntity> services) { this.services = services; }
    public LocalDateTime getDatetimeStart() { return datetimeStart; }
    public void setDatetimeStart(LocalDateTime datetimeStart) { this.datetimeStart = datetimeStart; }
    public LocalDateTime getDatetimeEnd() { return datetimeEnd; }
    public void setDatetimeEnd(LocalDateTime datetimeEnd) { this.datetimeEnd = datetimeEnd; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public ReviewEntity getReview() { return review; }
    public void setReview(ReviewEntity review) { this.review = review; }
}