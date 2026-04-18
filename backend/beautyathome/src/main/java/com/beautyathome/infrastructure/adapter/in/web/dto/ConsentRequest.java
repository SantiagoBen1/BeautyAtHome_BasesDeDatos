package com.beautyathome.infrastructure.adapter.in.web.dto;

/**
 * DTO used to request granting consent for photo publishing.
 */
public class ConsentRequest {

    private String bookingId;

    public ConsentRequest() {}

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
