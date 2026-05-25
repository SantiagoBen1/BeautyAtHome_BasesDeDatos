package com.beautyathome.services.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.beautyathome.dto.BookingRequest;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.repositories.ProfessionalRepository;

@Component
public class CoverageValidationHandler extends BookingRequestHandler {

    private final ProfessionalRepository professionalRepository;

    public CoverageValidationHandler(ProfessionalRepository professionalRepository) {
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