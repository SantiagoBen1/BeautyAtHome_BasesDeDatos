package application.booking.validation;

import application.booking.BookingRequest;
import domain.booking.AgendaSingleton;

/**
 * Handler that ensures the professional is available for the requested time
 * slot.
 */
public class AvailabilityValidationHandler extends BookingRequestHandler {

    private final AgendaSingleton agendaSingleton;
    private final int defaultDurationMinutes;

    /**
     * @param agendaSingleton agenda used to check availability
     * @param defaultDurationMinutes duration fallback when service length is not provided
     */
    public AvailabilityValidationHandler(AgendaSingleton agendaSingleton, int defaultDurationMinutes) {
        this.agendaSingleton = agendaSingleton;
        this.defaultDurationMinutes = defaultDurationMinutes;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        if (request.getDateTime() == null) {
            return false;
        }
        return agendaSingleton.availability(
                request.getProfessionalId(),
                request.getDateTime(),
                defaultDurationMinutes
        );
    }
}