package com.beautyathome.domain.client;

/**
 * Representa al cliente que agenda y paga los servicios de belleza.
 */
public class Client {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

    /**
     * Crea un cliente con identificador, nombre y correo definidos.
     *
     * @param id     identificador Ãºnico en la plataforma
     * @param name   nombre completo del cliente
     * @param email  correo electrónico de contacto y notificaciones
     * @param phone  teléfono de contacto
     * @param address dirección del cliente
     */
    public Client(String id, String name, String email, String phone, String address, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public Client(String id, String name, String email, String phone, String address) {
        this(id, name, email, phone, address, null);
    }

    /**
     * Constructor sin argumentos para frameworks de serializaciÃ³n.
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
     * @return email utilizado para comunicaciÃ³n y login
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return teléfono de contacto
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return dirección del cliente
     */
    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
