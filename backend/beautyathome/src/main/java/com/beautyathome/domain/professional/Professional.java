package com.beautyathome.domain.professional;


import java.util.List;

import com.beautyathome.domain.service.ServiceComponent;

/**
 * Contrato que toda profesional de belleza debe cumplir para ser ofertada.
 */
public interface Professional {

    /**
     * @return identificador Ãºnico de la profesional
     */
    String getId();

    /**
     * @return nombre comercial mostrado al cliente
     */
    String getName();

    /**
     * @return URL de la fotografÃ­a representativa
     */
    String getPhotoUrl();

    /**
     * @return resumen de la experiencia y certificaciones
     */
    String getExperienceSummary();

    /**
     * @return lista de Ã¡reas donde ofrece servicio a domicilio
     */
    List<CoverageArea> getCoverageAreas();

    /**
     * @return marca personal o salÃ³n con el que colabora
     */
    Brand getBrand();

    /**
     * @return catÃ¡logo de servicios que puede ejecutar
     */
    List<ServiceComponent> getServicesOffered();

    /**
     * @return name of the professional type
     */
    default String getTypeName() {
        return this.getClass().getSimpleName();
    }
}
