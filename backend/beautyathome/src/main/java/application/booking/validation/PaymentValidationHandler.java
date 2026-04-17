package application.booking.validation;

import application.booking.BookingRequest;
import infrastructure.persistence.dao.ClientDAO;

/**
 * Handler that simulates payment eligibility by checking both client existence
 * and the presence of a service identifier.
 */
public class PaymentValidationHandler extends BookingRequestHandler {

    private final ClientDAO clientDAO;

    /**
     * @param clientDAO DAO used for client existence checks
     */
    public PaymentValidationHandler(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        return clientDAO.findById(request.getClientId()) != null
                && request.getServiceId() != null;
    }
}
