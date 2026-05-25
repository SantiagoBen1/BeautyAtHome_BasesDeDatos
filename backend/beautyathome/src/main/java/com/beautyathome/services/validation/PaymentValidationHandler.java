package com.beautyathome.services.validation;

import com.beautyathome.dto.BookingRequest;
import com.beautyathome.repositories.ClientRepository;

/**
 * Handler that simulates payment eligibility by checking both client existence
 * and the presence of a service identifier.
 */
public class PaymentValidationHandler extends BookingRequestHandler {

    private final ClientRepository clientRepositoryPort;

    /**
     * @param clientRepositoryPort Repository used for client existence checks
     */
    public PaymentValidationHandler(ClientRepository clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        return clientRepositoryPort.findById(request.getClientId()) != null
                && request.getServiceIds().get(0) != null;
    }
}
