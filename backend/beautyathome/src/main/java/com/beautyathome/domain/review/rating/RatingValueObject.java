package domain.review.rating;

/**
 * Value object que representa la calificación de 1 a 5 estrellas.
 */
public class RatingValueObject {

	private final int value;

	/**
	 * Valida y encapsula el valor numérico de la calificación.
	 *
	 * @param value entero entre 1 y 5
	 */
	public RatingValueObject(int value) {
		if (value < 1 || value > 5) {
			throw new IllegalArgumentException("Rating must be between 1 and 5");
		}
		this.value = value;
	}

	/**
	 * @return valor entero de la calificación
	 */
	public int getValue() {
		return value;
	}
}
