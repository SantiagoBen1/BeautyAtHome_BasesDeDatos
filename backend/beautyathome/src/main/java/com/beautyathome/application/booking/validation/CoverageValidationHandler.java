package com.beautyathome.application.booking.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.beautyathome.application.booking.BookingRequest;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;

@Component
public class CoverageValidationHandler extends BookingRequestHandler {

    private final ProfessionalRepositoryPort professionalRepository;

    public CoverageValidationHandler(ProfessionalRepositoryPort professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        Optional<Professional> professionalOpt = professionalRepository.findById(request.getProfessionalId());
        
        if (professionalOpt.isEmpty() || professionalOpt.get().getCoverageAreas().stream().noneMatch(area -> area.getName().equalsIgnoreCase(request.getZone()))) {
            throw new IllegalArgumentException("El profesional no cubre esta área.");
        }
        return true;
    }
}