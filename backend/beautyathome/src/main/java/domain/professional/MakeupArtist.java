package domain.professional;

import java.util.List;

import domain.service.ServiceComponent;

/**
 * Profesional enfocada en maquillaje social y editorial.
 */
public class MakeupArtist implements Professional {

	private final String id;
	private final String name;
	private final String photoUrl;
	private final String experienceSummary;
	private final List<CoverageArea> coverageAreas;
	private final Brand brand;
	private final List<ServiceComponent> servicesOffered;

	/**
	 * Instancia a la maquillista con la información mostrada en catálogo.
	 *
	 * @param id                identificador único
	 * @param name              nombre comercial
	 * @param photoUrl          imagen usada en la ficha
	 * @param experienceSummary resumen de trayectoria
	 * @param coverageAreas     zonas donde atiende
	 * @param brand             marca con la que colabora
	 * @param servicesOffered   servicios que puede ejecutar
	 */
	public MakeupArtist(String id,
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
