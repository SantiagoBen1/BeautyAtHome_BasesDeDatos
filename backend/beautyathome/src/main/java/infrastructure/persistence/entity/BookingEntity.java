package infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    
    @Id
    private String id;
    private String clientId;
    private String professionalId;
    private LocalDateTime bookingDate;
    private String status; // Pending, Confirmed, etc.
    // TODO: Relacionar llaves foráneas completas con @ManyToOne tras el diagrama ER

    public BookingEntity() {}

    public BookingEntity(String id, String clientId, String professionalId, LocalDateTime bookingDate, String status) {
        this.id = id;
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters y Setters omitidos por brevedad (debes generarlos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getProfessionalId() { return professionalId; }
    public void setProfessionalId(String professionalId) { this.professionalId = professionalId; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate
; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}