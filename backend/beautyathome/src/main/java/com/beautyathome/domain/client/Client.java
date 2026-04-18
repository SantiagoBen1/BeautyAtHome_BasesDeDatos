package domain.client;

/**
 * Representa al cliente que agenda y paga los servicios de belleza.
 */
public class Client {

    private String id;
    private String name;
    private String email;

    /**
     * Crea un cliente con identificador, nombre y correo definidos.
     *
     * @param id     identificador único en la plataforma
     * @param name   nombre completo del cliente
     * @param email  correo electrónico de contacto y notificaciones
     */
    public Client(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Constructor sin argumentos para frameworks de serialización.
     */
    public Client() {}

    /**
     * @return identificador persistido del cliente
     */
    public String getId() {
        return id;
    }

    /**
     * @return nombre completo registrado
     */
    public String getName() {
        return name;
    }

    /**
     * @return email utilizado para comunicación y login
     */
    public String getEmail() {
        return email;
    }
}
