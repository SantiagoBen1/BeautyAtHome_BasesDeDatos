package domain.professional.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.professional.Brand;
import domain.professional.CoverageArea;
import domain.professional.HairStylist;
import domain.professional.MakeupArtist;
import domain.professional.Manicurist;
import domain.professional.Professional;
import domain.service.ServiceComponent;

/**
 * Implementación concreta que arma profesionales a partir de mapas flexibles.
 */
public class ConcreteProfessionalFactory implements ProfessionalAbstractFactory {

	/** {@inheritDoc} */
	@Override
	public Professional createProfessional(String type, Map<String, Object> data) {
		String normalized = type == null ? "" : type.toLowerCase();
		return switch (normalized) {
			case "hairstylist" -> buildProfessional(new HairStylistBuilder(), data);
			case "makeupartist" -> buildProfessional(new MakeupArtistBuilder(), data);
			case "manicurist" -> buildProfessional(new ManicuristBuilder(), data);
			default -> throw new IllegalArgumentException("Unsupported professional type: " + type);
		};
	}

	/**
	 * Llena un builder específico con los datos del mapa y genera la profesional.
	 *
	 * @param builder builder concreto según la especialidad
	 * @param data    mapa con atributos provenientes de formularios u orígenes externos
	 * @return profesional lista para publicar
	 */
	@SuppressWarnings("unchecked")
	private Professional buildProfessional(ProfessionalBuilder builder, Map<String, Object> data) {
		String id = (String) data.getOrDefault("id", UUID.randomUUID().toString());
		String name = (String) data.getOrDefault("name", "Unnamed");
		String photoUrl = (String) data.getOrDefault("photoUrl", "");
		String experience = (String) data.getOrDefault("experienceSummary", "");
		List<String> coverageNames = (List<String>) data.getOrDefault("coverage", Collections.emptyList());
		List<ServiceComponent> services = (List<ServiceComponent>) data.getOrDefault("services", Collections.emptyList());
		Map<String, Object> brandData = (Map<String, Object>) data.get("brand");
		Brand brand = null;
		if (brandData != null) {
			String brandName = (String) brandData.getOrDefault("name", "");
			String brandLogo = (String) brandData.getOrDefault("logo", "");
			List<String> heroProducts = Collections.emptyList();
			Object rawProducts = brandData.get("products");
			if (rawProducts instanceof List<?> list) {
				heroProducts = list.stream()
					.map(Object::toString)
					.collect(Collectors.toList());
			}
			brand = new Brand(brandName, brandLogo, heroProducts);
		}

		builder.setId(id)
				.setName(name)
				.setPhotoUrl(photoUrl)
				.setExperienceSummary(experience)
				.setCoverageAreas(buildCoverageAreas(coverageNames))
				.setBrand(brand)
				.setServices(services);
		return builder.build();
	}

	/**
	 * Convierte nombres de zonas en objetos {@link CoverageArea}.
	 *
	 * @param names etiquetas de barrios/sectores recibidas
	 * @return lista mutable de áreas para asignar a la profesional
	 */
	private List<CoverageArea> buildCoverageAreas(List<String> names) {
		List<CoverageArea> coverageAreas = new ArrayList<>();
		if (names == null) {
			return coverageAreas;
		}
		for (String name : names) {
			coverageAreas.add(new CoverageArea(name));
		}
		return coverageAreas;
	}

	/**
	 * Mini builder interno para no exponer una API adicional en el dominio.
	 */
	private interface ProfessionalBuilder {
		/** Configura el identificador. */
		ProfessionalBuilder setId(String id);
		/** Configura el nombre comercial. */
		ProfessionalBuilder setName(String name);
		/** Configura la foto de perfil. */
		ProfessionalBuilder setPhotoUrl(String photoUrl);
		/** Define el resumen profesional. */
		ProfessionalBuilder setExperienceSummary(String summary);
		/** Asigna las zonas de cobertura. */
		ProfessionalBuilder setCoverageAreas(List<CoverageArea> coverageAreas);
		/** Establece la marca asociada. */
		ProfessionalBuilder setBrand(Brand brand);
		/** Lista los servicios disponibles. */
		ProfessionalBuilder setServices(List<ServiceComponent> services);
		/** Construye la profesional final. */
		Professional build();
	}

	/**
	 * Builder específico para {@link HairStylist} que sirve como base reutilizable.
	 */
	private static class HairStylistBuilder implements ProfessionalBuilder {

		protected String id;
		protected String name;
		protected String photoUrl;
		protected String summary;
		protected List<CoverageArea> coverageAreas;
		protected Brand brand;
		protected List<ServiceComponent> services;

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setId(String id) {
			this.id = id;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setName(String name) {
			this.name = name;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setExperienceSummary(String summary) {
			this.summary = summary;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setCoverageAreas(List<CoverageArea> coverageAreas) {
			this.coverageAreas = coverageAreas;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setBrand(Brand brand) {
			this.brand = brand;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public ProfessionalBuilder setServices(List<ServiceComponent> services) {
			this.services = services;
			return this;
		}

		/** {@inheritDoc} */
		@Override
		public Professional build() {
			return new HairStylist(id, name, photoUrl, summary, coverageAreas, brand, services);
		}
	}

	/** Builder que genera maquillistas reciclando la lógica base. */
	private static class MakeupArtistBuilder extends HairStylistBuilder {
		/** {@inheritDoc} */
		@Override
		public Professional build() {
			return new MakeupArtist(id, name, photoUrl, summary, coverageAreas, brand, services);
		}
	}

	/** Builder que crea manicuristas reutilizando el comportamiento común. */
	private static class ManicuristBuilder extends HairStylistBuilder {
		/** {@inheritDoc} */
		@Override
		public Professional build() {
			return new Manicurist(id, name, photoUrl, summary, coverageAreas, brand, services);
		}
	}
}
