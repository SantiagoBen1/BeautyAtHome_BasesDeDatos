package domain.booking.observer;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import domain.booking.Booking;
import domain.professional.Professional;

/**
 * Observer that allows the platform to notify professionals whenever booking
 * changes take place.
 */
public class ProfessionalNotificationObserver implements NotificationObserver {

	private static final DateTimeFormatter DATE_FORMATTER =
		DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.forLanguageTag("es-CO"));

	private final Professional professional;

	/**
	 * @param professional professional to notify
	 */
	public ProfessionalNotificationObserver(Professional professional) {
		this.professional = professional;
	}

	@Override
	public void update(Booking booking) {
		if (booking == null || professional == null) {
			return;
		}
		StringBuilder message = new StringBuilder();
		message.append("Reserva ").append(booking.getId()).append(" actualizada a ")
			.append(resolveStateLabel(booking)).append('.');
		if (booking.getDateTime() != null) {
			message.append(" Próxima cita: ")
				.append(DATE_FORMATTER.format(booking.getDateTime()));
		}
		message.append(" Cliente: ").append(booking.getClientId());
		message.append(" Servicio: ").append(booking.getServiceId());

		System.out.printf("[PRO-NOTIFY] to=%s message=%s%n",
			professional.getName() == null ? "profesional" : professional.getName(),
			message);
	}

	private String resolveStateLabel(Booking booking) {
		if (booking.getState() == null) {
			return "pendiente";
		}
		return booking.getState().getClass().getSimpleName().replace("State", "").toLowerCase(Locale.ROOT);
	}
}
