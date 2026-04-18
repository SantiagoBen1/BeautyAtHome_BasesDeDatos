package api.dto;

/**
 * DTO that captures brand metadata associated with a professional.
 */
public class BrandRequest {

    private String name;
    private String logoUrl;

    public BrandRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
