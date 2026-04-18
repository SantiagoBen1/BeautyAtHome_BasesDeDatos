package application.booking;

import java.time.LocalDateTime;

/**
 * DTO that transports booking information from the API layer into the domain
 * service/validation pipeline.
 */
public class BookingRequest {

    private String clientId;
    private String professionalId;
    private String serviceId;
    private String zone;
    private LocalDateTime dateTime;

    /**
     * Default constructor used by serialization frameworks.
     */
    public BookingRequest() {
        // default constructor for frameworks and manual population
    }

    /**
     * Convenience constructor to populate all properties at once.
     */
    public BookingRequest(String clientId, String professionalId, String serviceId, String zone, LocalDateTime dateTime) {
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.serviceId = serviceId;
        this.zone = zone;
        this.dateTime = dateTime;
    }

    /**
     * @return identifier of the client requesting the service
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId identifier of the client requesting the service
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return professional identifier
     */
    public String getProfessionalId() {
        return professionalId;
    }

    /**
     * @param professionalId professional identifier
     */
    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    /**
     * @return identifier of the service to execute
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId identifier of the service to execute
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return geographical zone requested by the client
     */
    public String getZone() {
        return zone;
    }

    /**
     * @param zone geographical zone requested by the client
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * @return requested appointment date/time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime requested appointment date/time
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}