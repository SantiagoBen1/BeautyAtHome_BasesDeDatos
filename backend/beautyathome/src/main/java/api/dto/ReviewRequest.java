package api.dto;

/**
 * DTO used to collect the payload required to create a review.
 */
public class ReviewRequest {

    private String bookingId;
    private int rating;
    private String text;

    public ReviewRequest() {}

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
