package domain.service.image;

/**
 * Lightweight value object holding a URL to a supportive image for a service.
 */
public class ImageReference {

    private String url;

    public ImageReference(String url) {
        this.url = url;
    }

    public ImageReference() {}

    public String getUrl() {
        return url;
    }
}
