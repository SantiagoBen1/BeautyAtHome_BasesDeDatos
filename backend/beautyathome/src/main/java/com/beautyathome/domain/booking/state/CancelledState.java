package domain.booking.state;

import domain.booking.Booking;

/**
 * Terminal state indicating the appointment has been cancelled.
 */
public class CancelledState implements BookingState {

	@Override
	public void confirm(Booking booking) {
		// No-op: cannot confirm a cancelled booking
	}

	@Override
	public void start(Booking booking) {
		// No-op
	}

	@Override
	public void complete(Booking booking) {
		// No-op
	}

	@Override
	public void cancel(Booking booking) {
		// Already cancelled
	}
}
