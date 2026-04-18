package domain.professional;

import java.util.List;

import domain.service.ServiceComponent;

/**
 * Especialista en manicura y cuidado de uñas que atiende a domicilio.
 */
public class Manicurist implements Professional {

	private final String id;
	private final String name;
	private final String photoUrl;
	private final String experienceSummary;
	private final List<CoverageArea> coverageAreas;
	private final Brand brand;
	private final List<ServiceComponent> servicesOffered;

	/**
	 * Construye a la manicurista con los metadatos necesarios para la agenda.
	 *
	 * @param id                identificador único
	 * @param name              nombre de presentación
	 * @param photoUrl          fotografía destacada
	 * @param experienceSummary resumen de experiencia
	 * @param coverageAreas     zonas de servicio
	 * @param brand             marca aliada
	 * @param servicesOffered   catálogo de servicios que presta
	 */
	public Manicurist(String id,
				  String name,
				  String photoUrl,
				  String experienceSummary,
				  List<CoverageArea> coverageAreas,
				  Brand brand,
				  List<ServiceComponent> servicesOffered) {
		this.id = id;
		this.name = name;
		this.photoUrl = photoUrl;
		this.experienceSummary = experienceSummary;
		this.coverageAreas = coverageAreas;
		this.brand = brand;
		this.servicesOffered = servicesOffered;
	}

	/** {@inheritDoc} */
	@Override
	public String getId() {
		return id;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String getPhotoUrl() {
		return photoUrl;
	}

	/** {@inheritDoc} */
	@Override
	public String getExperienceSummary() {
		return experienceSummary;
	}

	/** {@inheritDoc} */
	@Override
	public List<CoverageArea> getCoverageAreas() {
		return coverageAreas;
	}

	/** {@inheritDoc} */
	@Override
	public Brand getBrand() {
		return brand;
	}

	/** {@inheritDoc} */
	@Override
	public List<ServiceComponent> getServicesOffered() {
		return servicesOffered;
	}
}
