package domain.service;

/**
 * Value object that names a service category (Haircut, Makeup, etc.).
 */
public class Category {

    private final String name;

    /**
     * @param name category name
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * @return category name
     */
    public String getName() {
        return name;
    }
}
