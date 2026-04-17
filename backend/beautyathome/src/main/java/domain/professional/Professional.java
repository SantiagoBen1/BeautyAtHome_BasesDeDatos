package domain.professional;


import java.util.List;

import domain.service.ServiceComponent;

/**
 * Contrato que toda profesional de belleza debe cumplir para ser ofertada.
 */
public interface Professional {

    /**
     * @return identificador único de la profesional
     */
    String getId();

    /**
     * @return nombre comercial mostrado al cliente
     */
    String getName();

    /**
     * @return URL de la fotografía representativa
     */
    String getPhotoUrl();

    /**
     * @return resumen de la experiencia y certificaciones
     */
    String getExperienceSummary();

    /**
     * @return lista de áreas donde ofrece servicio a domicilio
     */
    List<CoverageArea> getCoverageAreas();

    /**
     * @return marca personal o salón con el que colabora
     */
    Brand getBrand();

    /**
     * @return catálogo de servicios que puede ejecutar
     */
    List<ServiceComponent> getServicesOffered();
}
