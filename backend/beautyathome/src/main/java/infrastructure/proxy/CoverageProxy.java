package infrastructure.proxy;

import domain.professional.CoverageArea;
import domain.professional.Professional;

/**
 * Proxy que encapsula la verificación de cobertura geográfica.
 */
public class CoverageProxy {

	private final Professional professional;

	/**
	 * @param professional profesional cuya cobertura se consultará
	 */
	public CoverageProxy(Professional professional) {
		this.professional = professional;
	}

	/**
	 * Indica si la profesional atiende la zona recibida.
	 *
	 * @param zone nombre de la zona
	 * @return {@code true} si existe cobertura
	 */
	public boolean isAvailable(String zone) {
		if (zone == null || zone.isBlank()) {
			return false;
		}
		if (professional.getCoverageAreas() == null) {
			return false;
		}
		for (CoverageArea area : professional.getCoverageAreas()) {
			if (area.getName().equalsIgnoreCase(zone)) {
				return true;
			}
		}
		return false;
	}
}
