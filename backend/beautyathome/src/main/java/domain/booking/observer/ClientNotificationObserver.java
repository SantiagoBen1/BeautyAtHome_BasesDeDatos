package domain.booking.observer;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import domain.booking.Booking;
import domain.client.Client;

/**
 * Observer that represents the client as a notification target. It can later
 * be extended to send e-mails, push notifications, etc.
 */
public class ClientNotificationObserver implements NotificationObserver {

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.forLanguageTag("es-CO"));

    private final Client client;

    /**
     * @param client client to notify when booking updates occur
     */
    public ClientNotificationObserver(Client client) {
        this.client = client;
    }

    @Override
    public void update(Booking booking) {
        if (booking == null || client == null) {
            return;
        }
        String subject = "Actualización de tu reserva " + booking.getId();
        StringBuilder body = new StringBuilder();
        body.append("Hola ").append(client.getName() == null ? "Cliente" : client.getName()).append(",");
        body.append(System.lineSeparator());
        body.append("Tu servicio queda marcado como ").append(resolveStateLabel(booking)).append(".");
        if (booking.getDateTime() != null) {
            body.append(" Agenda: ")
                .append(DATE_FORMATTER.format(booking.getDateTime()));
        }
        body.append(" Puedes responder a este correo para reprogramar.");

        logNotification(subject, body.toString());
    }

    private void logNotification(String subject, String body) {
        String email = client.getEmail() == null ? "sin-email" : client.getEmail();
        System.out.printf("[CLIENT-NOTIFY] to=%s subject=%s body=%s%n", email, subject, body);
    }

    private String resolveStateLabel(Booking booking) {
        if (booking.getState() == null) {
            return "pendiente";
        }
        return booking.getState().getClass().getSimpleName().replace("State", "").toLowerCase(Locale.ROOT);
    }
}
