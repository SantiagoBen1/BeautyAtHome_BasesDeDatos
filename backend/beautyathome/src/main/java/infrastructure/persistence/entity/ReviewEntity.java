package infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class ReviewEntity {
    
    @Id
    private String id;
    private String bookingId;
    private Integer rating;
    private String comment;

    public ReviewEntity() {}

    public ReviewEntity(String id, String bookingId, Integer rating, String comment) {
        this.id = id;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}