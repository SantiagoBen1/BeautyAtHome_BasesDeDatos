package domain.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import domain.booking.observer.BookingSubject;
import domain.booking.observer.NotificationObserver;
import domain.booking.state.BookingState;
import domain.booking.state.PendingState;

/**
 * Aggregate root that represents a scheduled service between a client and a
 * professional. Implements the observer pattern to notify stakeholders and the
 * state pattern to manage lifecycle transitions.
 */
public class Booking implements BookingSubject {

    private final String id;
    private final String clientId;
    private final String professionalId;
    private final String serviceId;
    private LocalDateTime dateTime;

    private BookingState state;
    private final List<NotificationObserver> observers = new ArrayList<>();

    /**
     * Creates a booking in the pending state.
     *
     * @param id unique identifier
     * @param clientId client owning the booking
     * @param professionalId assigned professional
     * @param serviceId service identifier
     * @param dateTime scheduled date/time
     */
    public Booking(String id, String clientId, String professionalId, String serviceId, LocalDateTime dateTime) {
        this.id = id;
        this.clientId = clientId;
        this.professionalId = professionalId;
        this.serviceId = serviceId;
        this.dateTime = dateTime;
        this.state = new PendingState();
    }

    /**
     * @return booking identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return client identifier linked to the booking
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return professional identifier linked to the booking
     */
    public String getProfessionalId() {
        return professionalId;
    }

    /**
     * @return service identifier booked
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @return scheduled date/time for the booking
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Updates the scheduled date/time, typically after rescheduling.
     *
     * @param dateTime new date/time value
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return current lifecycle state
     */
    public BookingState getState() {
        return state;
    }

    /**
     * Manually sets the booking state, used by state transitions.
     *
     * @param state new booking state
     */
    public void setState(BookingState state) {
        this.state = state;
    }

    /**
     * Delegates confirmation logic to the current state implementation.
     */
    public void confirm() {
        state.confirm(this);
    }

    /**
     * Delegates start logic to the current state implementation.
     */
    public void start() {
        state.start(this);
    }

    /**
     * Delegates completion logic to the current state implementation.
     */
    public void complete() {
        state.complete(this);
    }

    /**
     * Delegates cancellation logic to the current state implementation.
     */
    public void cancel() {
        state.cancel(this);
    }

    @Override
    /**
     * Registers an observer interested in booking lifecycle events.
     *
     * @param observer observer to attach
     */
    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    /**
     * Removes the provided observer from the notification list.
     *
     * @param observer observer to detach
     */
    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    /**
     * Notifies all observers of the latest booking change.
     */
    public void notifyObservers() {
        for (NotificationObserver o : observers) {
            o.update(this);
        }
    }
}