package ui.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.booking.Booking;
import domain.booking.state.CancelledState;
import domain.booking.state.CompletedState;
import domain.booking.state.ConfirmedState;
import domain.booking.state.InProgressState;
import domain.booking.state.PendingState;

/**
 * View model that groups bookings by lifecycle status to display a clear board in the UI.
 */
public class BookingLane {

    private final StatusCategory category;
    private final List<TimelineCard> cards;

    private BookingLane(StatusCategory category, List<Booking> bookings) {
        this.category = category;
        this.cards = bookings == null
                ? List.of()
                : bookings.stream()
                    .map(booking -> new TimelineCard(booking, describeState(booking)))
                    .collect(Collectors.toList());
    }

    /**
     * Builds lanes for each meaningful booking status category.
     *
     * @param bookings source data from the DAO
     * @return ordered list of lanes ready for the template
     */
    public static List<BookingLane> from(List<Booking> bookings) {
        Map<StatusCategory, List<Booking>> grouped = new EnumMap<>(StatusCategory.class);
        for (StatusCategory category : StatusCategory.values()) {
            grouped.put(category, new ArrayList<>());
        }
        if (bookings != null) {
            for (Booking booking : bookings) {
                grouped.get(resolveCategory(booking)).add(booking);
            }
        }
        return Arrays.stream(StatusCategory.values())
                .map(category -> new BookingLane(category, grouped.get(category)))
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return category.title;
    }

    public String getDescription() {
        return category.description;
    }

    public String getEmptyLabel() {
        return category.emptyLabel;
    }

    public String getBadgeTone() {
        return category.badgeTone;
    }

    public String getKey() {
        return category.key;
    }

    public List<TimelineCard> getCards() {
        return cards;
    }

    private static StatusCategory resolveCategory(Booking booking) {
        if (booking == null || booking.getState() == null) {
            return StatusCategory.PENDING;
        }
        if (booking.getState() instanceof CompletedState) {
            return StatusCategory.COMPLETED;
        }
        if (booking.getState() instanceof CancelledState) {
            return StatusCategory.CANCELLED;
        }
        return StatusCategory.PENDING;
    }

    private static StateDescriptor describeState(Booking booking) {
        if (booking == null || booking.getState() == null) {
            return StateDescriptor.pending();
        }
        if (booking.getState() instanceof CompletedState) {
            return StateDescriptor.completed();
        }
        if (booking.getState() instanceof CancelledState) {
            return StateDescriptor.cancelled();
        }
        if (booking.getState() instanceof InProgressState) {
            return new StateDescriptor("En curso", "warning", "is-pending");
        }
        if (booking.getState() instanceof ConfirmedState) {
            return new StateDescriptor("Confirmada", "warning", "is-pending");
        }
        if (booking.getState() instanceof PendingState) {
            return StateDescriptor.pending();
        }
        return StateDescriptor.pending();
    }

    private enum StatusCategory {
        PENDING("pending",
                "Reservas activas",
                "Asignaciones en espera de ejecución o confirmación.",
                "Aún no hay reservas en trámite",
                "warning"),
        COMPLETED("completed",
                "Experiencias entregadas",
                "Listas para reseñas y publicación de resultados.",
                "No hay servicios completados todavía",
                "success"),
        CANCELLED("cancelled",
                "Cancelaciones",
                "Liberaron agenda; revisar motivo y reprogramar.",
                "Ninguna reserva ha sido cancelada",
                "danger");

        private final String key;
        private final String title;
        private final String description;
        private final String emptyLabel;
        private final String badgeTone;

        StatusCategory(String key, String title, String description, String emptyLabel, String badgeTone) {
            this.key = key;
            this.title = title;
            this.description = description;
            this.emptyLabel = emptyLabel;
            this.badgeTone = badgeTone;
        }
    }

    private record StateDescriptor(String label, String tone, String timelineModifier) {

        static StateDescriptor pending() {
            return new StateDescriptor("Pendiente", "warning", "is-pending");
        }

        static StateDescriptor completed() {
            return new StateDescriptor("Completada", "success", "is-completed");
        }

        static StateDescriptor cancelled() {
            return new StateDescriptor("Cancelada", "danger", "is-cancelled");
        }
    }

    /**
     * Represents a single booking rendered as a card within the lane.
     */
    public static class TimelineCard {
        private final Booking booking;
        private final String statusLabel;
        private final String statusTone;
        private final String timelineModifier;

        TimelineCard(Booking booking, StateDescriptor descriptor) {
            this.booking = booking;
            this.statusLabel = descriptor.label();
            this.statusTone = descriptor.tone();
            this.timelineModifier = descriptor.timelineModifier();
        }

        public Booking getBooking() {
            return booking;
        }

        public String getStatusLabel() {
            return statusLabel;
        }

        public String getStatusTone() {
            return statusTone;
        }

        public String getTimelineModifier() {
            return timelineModifier;
        }
    }
}
