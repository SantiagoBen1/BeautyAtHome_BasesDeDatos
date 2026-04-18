package com.beautyathome.application.booking.validation;

import com.beautyathome.application.booking.BookingRequest;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;

/**
 * Handler that simulates payment eligibility by checking both client existence
 * and the presence of a service identifier.
 */
public class PaymentValidationHandler extends BookingRequestHandler {

    private final ClientRepositoryPort clientRepositoryPort;

    /**
     * @param clientRepositoryPort Repository used for client existence checks
     */
    public PaymentValidationHandler(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        return clientRepositoryPort.findById(request.getClientId()) != null
                && request.getServiceId() != null;
    }
}
