package ui.viewmodel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Form backing bean that captures professional registration data from the MVC layer.
 */
public class ProfessionalForm {

    private String id;
    private String type;
    private String name;
    private String photoUrl;
    private String experienceSummary;
    private String coverageAreas; // comma separated
    private String brandName;
    private String brandLogo;
    private String brandProducts;
    private String catalogSignature;

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

    public String getCoverageAreas() {
        return coverageAreas;
    }

    public void setCoverageAreas(String coverageAreas) {
        this.coverageAreas = coverageAreas;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandProducts() {
        return brandProducts;
    }

    public void setBrandProducts(String brandProducts) {
        this.brandProducts = brandProducts;
    }

    public String getCatalogSignature() {
        return catalogSignature;
    }

    public void setCatalogSignature(String catalogSignature) {
        this.catalogSignature = catalogSignature;
    }

    /**
     * @return coverage areas split by comma and trimmed
     */
    public List<String> coverageAsList() {
        if (coverageAreas == null || coverageAreas.isBlank()) {
            return List.of();
        }
        return Arrays.stream(coverageAreas.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * @return lista de productos patrocinados separados por coma.
     */
    public List<String> brandProductsAsList() {
        if (brandProducts == null || brandProducts.isBlank()) {
            return List.of();
        }
        return Arrays.stream(brandProducts.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * @return lista de servicios declarados en el catálogo signature.
     */
    public List<CatalogEntry> catalogEntries() {
        if (catalogSignature == null || catalogSignature.isBlank()) {
            return List.of();
        }
        return Arrays.stream(catalogSignature.split("\\r?\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(this::parseCatalogLine)
                .filter(entry -> entry != null)
                .collect(Collectors.toList());
    }

    private CatalogEntry parseCatalogLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) {
            return null;
        }
        String name = parts[0].trim();
        String description = parts[1].trim();
        double price = sanitizeDouble(parts[2]);
        int duration = sanitizeInt(parts[3]);
        if (name.isEmpty() || description.isEmpty() || price <= 0 || duration <= 0) {
            return null;
        }
        List<String> images = parts.length >= 5
                ? Arrays.stream(parts[4].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList())
                : List.of();
        return new CatalogEntry(name, description, price, duration, images);
    }

    private double sanitizeDouble(String raw) {
        if (raw == null) {
            return -1;
        }
        String normalized = raw.replaceAll("[^0-9.,]", "").replace(',', '.');
        if (normalized.isBlank()) {
            return -1;
        }
        try {
            return Double.parseDouble(normalized);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private int sanitizeInt(String raw) {
        if (raw == null) {
            return -1;
        }
        String normalized = raw.replaceAll("[^0-9]", "");
        if (normalized.isBlank()) {
            return -1;
        }
        try {
            return Integer.parseInt(normalized);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * DTO interno para transportar definiciones de servicios.
     */
    public record CatalogEntry(String name,
                               String description,
                               double price,
                               int durationMinutes,
                               List<String> imageUrls) { }
}
