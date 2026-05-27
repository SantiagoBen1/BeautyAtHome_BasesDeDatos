package com.beautyathome.services.validation;

import org.springframework.stereotype.Component;

import com.beautyathome.dto.BookingRequest;

@Component
public class ConsentValidationHandler extends BookingRequestHandler {

    @Override
    protected boolean doHandle(BookingRequest request) {
        if (!request.isConsentGiven()) {
            throw new IllegalArgumentException("El cliente debe dar su consentimiento para el servicio en casa.");
        }
        return true;
    }
}