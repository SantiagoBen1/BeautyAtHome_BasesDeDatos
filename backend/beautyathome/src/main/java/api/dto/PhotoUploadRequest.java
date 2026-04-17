package api.dto;

/**
 * DTO for photo uploads tied to a booking.
 */
public class PhotoUploadRequest {

    private String bookingId;
    private String url;
    private boolean isPublic;

    public PhotoUploadRequest() {}

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
