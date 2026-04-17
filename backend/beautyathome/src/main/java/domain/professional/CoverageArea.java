package domain.professional;

/**
 * Describe una zona geográfica donde la profesional atiende a domicilio.
 */
public class CoverageArea {

    private String name;

    /**
     * Crea una cobertura identificada por su nombre o barrio.
     *
     * @param name etiqueta amigable para mostrar al cliente
     */
    public CoverageArea(String name) {
        this.name = name;
    }

    /**
     * Constructor por defecto para serialización.
     */
    public CoverageArea() {}

    /**
     * @return nombre legible del sector
     */
    public String getName() {
        return name;
    }
}
