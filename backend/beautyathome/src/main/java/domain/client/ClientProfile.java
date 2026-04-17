package domain.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Perfil enriquecido del cliente con preferencias y consentimientos.
 */
public class ClientProfile {

	private final Client client;
	private final Set<String> preferredCategories = new HashSet<>();
	private boolean marketingConsent;

	/**
	 * Construye el perfil para un {@link Client} existente.
	 *
	 * @param client referencia al cliente base
	 */
	public ClientProfile(Client client) {
		this.client = client;
	}

	/**
	 * @return cliente al que pertenece este perfil
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Registra una categoría preferida para personalizar recomendaciones.
	 *
	 * @param categoryName nombre de la categoría (p. ej. "Makeup")
	 */
	public void addPreferredCategory(String categoryName) {
		preferredCategories.add(categoryName);
	}

	/**
	 * @return conjunto inmutable con las categorías preferidas
	 */
	public Set<String> getPreferredCategories() {
		return Collections.unmodifiableSet(preferredCategories);
	}

	/**
	 * @return true si el cliente autorizó comunicaciones comerciales
	 */
	public boolean hasMarketingConsent() {
		return marketingConsent;
	}

	/**
	 * Define si el cliente acepta recibir campañas.
	 *
	 * @param marketingConsent indicador de consentimiento
	 */
	public void setMarketingConsent(boolean marketingConsent) {
		this.marketingConsent = marketingConsent;
	}
}
