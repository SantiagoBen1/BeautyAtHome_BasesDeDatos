package domain.professional;

import java.util.Collections;
import java.util.List;

/**
 * Marca o salón que respalda a la profesional (branding, logo y kit de productos).
 */
public class Brand {

    private String name;
    private String logoUrl;
    private List<String> heroProducts = List.of();

    /**
     * Crea una marca con nombre, logotipo y listado de productos insignia.
     *
     * @param name nombre del salón o marca personal
     * @param logoUrl URL pública del logotipo
     * @param heroProducts productos exclusivos utilizados por la profesional
     */
    public Brand(String name, String logoUrl, List<String> heroProducts) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.heroProducts = heroProducts == null ? List.of() : List.copyOf(heroProducts);
    }

    /**
     * Crea una marca cuando solo se conoce nombre y logo.
     */
    public Brand(String name, String logoUrl) {
        this(name, logoUrl, Collections.emptyList());
    }

    /**
     * Constructor requerido por frameworks de persistencia.
     */
    public Brand() {}

    /**
     * @return nombre comercial
     */
    public String getName() {
        return name;
    }

    /**
     * @return URL del logo que se mostrará en la app
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @return productos exclusivos asociados a la marca
     */
    public List<String> getHeroProducts() {
        return heroProducts;
    }
}

