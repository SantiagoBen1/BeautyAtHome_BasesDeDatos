package com.beautyathome.services.proxy;

import com.beautyathome.domain.professional.CoverageArea;
import com.beautyathome.domain.professional.Professional;

/**
 * Proxy que encapsula la verificaciÃ³n de cobertura geogrÃ¡fica.
 */
public class CoverageProxy {

	private final Professional professional;

	/**
	 * @param professional profesional cuya cobertura se consultarÃ¡
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
