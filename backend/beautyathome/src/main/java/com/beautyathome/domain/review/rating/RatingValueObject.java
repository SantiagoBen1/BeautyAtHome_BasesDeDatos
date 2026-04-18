package com.beautyathome.domain.review.rating;

/**
 * Value object que representa la calificaciÃ³n de 1 a 5 estrellas.
 */
public class RatingValueObject {

	private final int value;

	/**
	 * Valida y encapsula el valor numÃ©rico de la calificaciÃ³n.
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
	 * @return valor entero de la calificaciÃ³n
	 */
	public int getValue() {
		return value;
	}
}
