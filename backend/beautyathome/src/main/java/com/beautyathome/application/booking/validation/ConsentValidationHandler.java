package application.booking.validation;

import application.booking.BookingRequest;
import infrastructure.persistence.dao.ClientDAO;

/**
 * Basic guard that ensures the client exists (and therefore has accepted the
 * terms/consents) before proceeding.
 */
public class ConsentValidationHandler extends BookingRequestHandler {

    private final ClientDAO clientDAO;

    /**
     * @param clientDAO DAO used to verify the client identity
     */
    public ConsentValidationHandler(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    protected boolean doHandle(BookingRequest request) {
        return clientDAO.findById(request.getClientId()) != null;
    }
}
