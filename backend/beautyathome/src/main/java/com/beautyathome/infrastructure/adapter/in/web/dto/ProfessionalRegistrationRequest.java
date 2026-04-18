package api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that captures the fields required to register a new professional.
 */
public class ProfessionalRegistrationRequest {

    private String id;
    private String type;
    private String name;
    private String photoUrl;
    private String experienceSummary;
    private List<String> coverageAreas = new ArrayList<>();
    private BrandRequest brand;

    public ProfessionalRegistrationRequest() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getExperienceSummary() {
        return experienceSummary;
    }

    public void setExperienceSummary(String experienceSummary) {
        this.experienceSummary = experienceSummary;
    }

    public List<String> getCoverageAreas() {
        return coverageAreas;
    }

    public void setCoverageAreas(List<String> coverageAreas) {
        this.coverageAreas = coverageAreas;
    }

    public BrandRequest getBrand() {
        return brand;
    }

    public void setBrand(BrandRequest brand) {
        this.brand = brand;
    }
}
