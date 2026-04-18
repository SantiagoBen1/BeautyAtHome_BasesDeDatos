package domain.service.image;

/**
 * Immutable representation of a captured service photo along with its sharing
 * metadata.
 */
public class Photo {

	private final String bookingId;
	private final String professionalId;
	private final String url;
	private final boolean isPublic;

	/**
	 * @param bookingId booking associated with the photo
	 * @param professionalId professional who executed the service
	 * @param url storage location of the image
	 * @param isPublic whether the client authorized public usage
	 */
	public Photo(String bookingId, String professionalId, String url, boolean isPublic) {
		this.bookingId = bookingId;
		this.professionalId = professionalId;
		this.url = url;
		this.isPublic = isPublic;
	}

	public String getBookingId() {
		return bookingId;
	}

	public String getProfessionalId() {
		return professionalId;
	}

	public String getUrl() {
		return url;
	}

	public boolean isPublic() {
		return isPublic;
	}
}
