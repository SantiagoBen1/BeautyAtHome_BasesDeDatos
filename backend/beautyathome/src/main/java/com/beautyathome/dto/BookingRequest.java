package com.beautyathome.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO that transports booking information from the API layer into the domain
 * service/validation pipeline.
 */
public class BookingRequest {

    private String clientId;
    private String professionalId;
    private List<String> serviceIds;
    private String zone;
    private LocalDateTime dateTime;
    private boolean consentGiven;

    /**
     * Default constructor used by serialization frameworks.
     */
    public BookingRequest() {
        // default constructor for frameworks and manual population
    }

    /**
     * Convenience constructor to populate all properties at once.
     */
    public BookingRequest(String clientId, String professionalId, List<String> serviceIds, String zone, LocalDateTime dateTime) {
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.serviceIds = serviceIds;
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
     * @return identifiers of the services to execute
     */
    public List<String> getServiceIds() {
        return serviceIds;
    }

    /**
     * @param serviceIds identifiers of the services to execute
     */
    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
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

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
}