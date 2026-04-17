package application.booking.validation;


import application.booking.BookingRequest;
import domain.professional.Professional;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.proxy.CoverageProxy;

/**
 * Ensures the selected professional provides coverage in the requested zone.
 */
public class CoverageValidationHandler extends BookingRequestHandler {

    private final ProfessionalDAO professionalDAO;

    /**
     * @param professionalDAO DAO used to load professional coverage metadata
     */
    public CoverageValidationHandler(ProfessionalDAO professionalDAO) {
        this.professionalDAO = professionalDAO;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        Professional professional = professionalDAO.findById(request.getProfessionalId());
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
