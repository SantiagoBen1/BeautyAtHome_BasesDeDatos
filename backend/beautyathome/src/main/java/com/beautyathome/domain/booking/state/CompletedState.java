package domain.booking.state;

import domain.booking.Booking;

/**
 * Terminal state that indicates the service has been fully delivered.
 */
public class CompletedState implements BookingState {

	@Override
	public void confirm(Booking booking) {
		// Completed bookings cannot go back
	}

	@Override
	public void start(Booking booking) {
		// Completed bookings cannot restart
	}

	@Override
	public void complete(Booking booking) {
		// Already completed
	}

	@Override
	public void cancel(Booking booking) {
		// Completed bookings cannot be cancelled retroactively
	}
}
