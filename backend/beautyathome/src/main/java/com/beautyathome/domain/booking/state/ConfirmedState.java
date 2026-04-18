package domain.booking.state;

import domain.booking.Booking;

/**
 * State representing a confirmed appointment that can either start, be
 * cancelled, or marked as completed.
 */
public class ConfirmedState implements BookingState {

	@Override
	public void confirm(Booking booking) {
		// Already confirmed
	}

	@Override
	public void start(Booking booking) {
		booking.setState(new InProgressState());
		booking.notifyObservers();
	}

	@Override
	public void complete(Booking booking) {
		booking.setState(new CompletedState());
		booking.notifyObservers();
	}

	@Override
	public void cancel(Booking booking) {
		booking.setState(new CancelledState());
		booking.notifyObservers();
	}
}
