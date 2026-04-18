package com.beautyathome.application.booking.validation;

import com.beautyathome.application.booking.BookingRequest;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;

/**
 * Basic guard that ensures the client exists (and therefore has accepted the
 * terms/consents) before proceeding.
 */
public class ConsentValidationHandler extends BookingRequestHandler {

    private final ClientRepositoryPort clientRepositoryPort;

    /**
     * @param clientRepositoryPort Repository used to verify the client identity
     */
    public ConsentValidationHandler(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        return clientRepositoryPort+.findById(request.getClientId()) != null;
    }
}
