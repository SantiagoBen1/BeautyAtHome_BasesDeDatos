package com.beautyathome.domain.booking;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import com.beautyathome.domain.service.ServiceComponent;

/**
 * Fluent builder that simplifies the creation of {@link Booking} instances by
 * progressively setting each required attribute.
 */
public class BookingBuilder {

    private String id;
    private String clientId;
    private String professionalId;
    private List<String> serviceIds = new ArrayList<>();
    private LocalDateTime dateTime;

    /**
     * Specifies the booking identifier the builder should reuse.
     *
     * @param id custom identifier (optional)
     * @return builder instance for chaining
     */
    public BookingBuilder withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the client identifier.
     *
     * @param clientId client identifier
     * @return builder instance
     */
    public BookingBuilder withClient(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Sets the professional identifier.
     *
     * @param professionalId professional identifier
     * @return builder instance
     */
    public BookingBuilder withProfessional(String professionalId) {
        this.professionalId = professionalId;
        return this;
    }

    /**
     * Indicates the booked services by their identifiers.
     *
     * @param serviceIds list of service identifiers
     * @return builder instance
     */
    public BookingBuilder withServices(List<String> serviceIds) {
        this.serviceIds = serviceIds;
        return this;
    }

    /**
     * Convenience method that extracts the identifier from a service instance.
     *
     * @param service service component (optional)
     * @return builder instance
     */
    public BookingBuilder withService(ServiceComponent service) {
        if (service != null) {
            this.serviceIds.add(service.getName());
        }
        return this;
    }

    /**
     * Sets the scheduled date/time for the booking.
     *
     * @param dateTime appointment date/time
     * @return builder instance
     */
    public BookingBuilder withDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    /**
     * Creates the {@link Booking} using collected attributes, generating an ID
     * automatically when missing.
     *
     * @return newly built booking
     */
    public Booking build() {
        String safeId = id == null ? UUID.randomUUID().toString() : id;
        return new Booking(safeId, clientId, professionalId, serviceIds, dateTime);
    }
}
