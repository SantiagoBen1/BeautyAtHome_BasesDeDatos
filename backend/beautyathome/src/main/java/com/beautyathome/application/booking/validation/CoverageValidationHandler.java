package com.beautyathome.application.booking.validation;


import com.beautyathome.application.booking.BookingRequest;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;
import com.beautyathome.infrastructure.proxy.CoverageProxy;
            
/**
 * Ensures the selected professional provides coverage in the requested zone.
 */
public class CoverageValidationHandler extends BookingRequestHandler {

    private final ProfessionalRepositoryPort professionalRepositoryPort;

    /**
     * @param professionalRepositoryPort Repository used to load professional coverage metadata
     */
    public CoverageValidationHandler(ProfessionalRepositoryPort professionalRepositoryPort) {
        this.professionalRepositoryPort = professionalRepositoryPort;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        Professional professional = professionalRepositoryPort.findById(request.getProfessionalId());
        if (professional == null) {
            return false;
        }
        if (request.getZone() == null || request.getZone().isBlank()) {
            return true;
        }
        CoverageProxy proxy = new CoverageProxy(professional);
        return proxy.isAvailable(request.getZone());
    }
}
