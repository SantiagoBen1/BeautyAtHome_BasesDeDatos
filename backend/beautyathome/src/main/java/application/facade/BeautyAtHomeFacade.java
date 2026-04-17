package application.facade;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import application.booking.BookingRequest;
import application.booking.BookingService;
import domain.booking.AgendaSingleton;
import domain.booking.Booking;
import domain.booking.command.CancelBookingCommand;
import domain.booking.command.CommandInvoker;
import domain.booking.history.ServiceHistory;
import domain.booking.observer.ClientNotificationObserver;
import domain.booking.observer.ProfessionalNotificationObserver;
import domain.client.Client;
import domain.pricing.PricingStrategy;
import domain.professional.Professional;
import domain.professional.factory.ProfessionalAbstractFactory;
import domain.review.Review;
import domain.service.ServiceComponent;
import domain.service.ServiceLeaf;
import domain.service.builder.ServiceDirector;
import domain.service.image.Photo;
import infrastructure.media.ConsentProxy;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;
import infrastructure.persistence.dao.ServiceDAO;
import infrastructure.proxy.CoverageProxy;
import infrastructure.proxy.ReviewGuardProxy;

/**
 * Fachada que expone casos de uso de Beauty At Home hacia controladores o UI.
 * Orquesta servicios de dominio, DAOs y proxies para mantener bajo acoplamiento.
 */
public class BeautyAtHomeFacade {

    private final ClientDAO clientDAO;
    private final ProfessionalDAO professionalDAO;
    private final ServiceDAO serviceDAO;
    private final BookingDAO bookingDAO;
    private final ReviewDAO reviewDAO;
    private final BookingService bookingService;
    private final PricingStrategy pricingStrategy;
    private final ProfessionalAbstractFactory professionalFactory;
    private final ServiceDirector serviceDirector;
    private final ReviewGuardProxy reviewGuardProxy;
    private final ConsentProxy consentProxy;
    private final CommandInvoker commandInvoker;
    private final AgendaSingleton agendaSingleton;

    /**
     * Ensambla la fachada con todas sus dependencias colaboradoras.
     *
     * @param clientDAO DAO de clientes
     * @param professionalDAO DAO de profesionales
     * @param serviceDAO DAO de servicios
     * @param bookingDAO DAO de reservas
     * @param reviewDAO DAO de reseñas
     * @param bookingService servicio de aplicación para reservas
     * @param pricingStrategy estrategia de precios activa
     * @param professionalFactory fábrica para crear profesionales
     * @param serviceDirector director para construir servicios básicos
     * @param reviewGuardProxy proxy que evita reseñas duplicadas
     * @param consentProxy proxy encargado de fotos y consentimientos
    * @param commandInvoker invocador que ejecuta los comandos de agenda
    * @param agendaSingleton agenda compartida que actúa como receptor
     */
    public BeautyAtHomeFacade(ClientDAO clientDAO,
                              ProfessionalDAO professionalDAO,
                              ServiceDAO serviceDAO,
                              BookingDAO bookingDAO,
                              ReviewDAO reviewDAO,
                              BookingService bookingService,
                              PricingStrategy pricingStrategy,
                              ProfessionalAbstractFactory professionalFactory,
                              ServiceDirector serviceDirector,
                              ReviewGuardProxy reviewGuardProxy,
                              ConsentProxy consentProxy,
                              CommandInvoker commandInvoker,
                              AgendaSingleton agendaSingleton) {
        this.clientDAO = clientDAO;
        this.professionalDAO = professionalDAO;
        this.serviceDAO = serviceDAO;
        this.bookingDAO = bookingDAO;
        this.reviewDAO = reviewDAO;
        this.bookingService = bookingService;
        this.pricingStrategy = pricingStrategy;
        this.professionalFactory = professionalFactory;
        this.serviceDirector = serviceDirector;
        this.reviewGuardProxy = reviewGuardProxy;
        this.consentProxy = consentProxy;
        this.commandInvoker = commandInvoker;
        this.agendaSingleton = agendaSingleton;
    }

    /**
     * Registra un cliente a partir de datos dinámicos (p. ej. JSON de API).
     *
     * @param data mapa con id, nombre y correo
     * @return cliente persistido
     */
    public Client registerClient(Map<String, Object> data) {
        Client client = new Client(
                (String) data.get("id"),
                (String) data.getOrDefault("name", "Anonymous"),
                (String) data.getOrDefault("email", "")
        );
        return registerClient(client);
    }

    /**
     * Persiste un cliente asegurando que tenga identificador.
     *
     * @param client entidad a guardar
     * @return cliente persistido con id válido
     */
    public Client registerClient(Client client) {
        Objects.requireNonNull(client, "client");
        Client normalized = client.getId() == null || client.getId().isBlank()
                ? new Client(UUID.randomUUID().toString(), client.getName(), client.getEmail())
                : client;
        return clientDAO.save(normalized);
    }

    /**
     * Registra una profesional a partir de su payload y tipo declarado.
     *
     * @param data mapa con tipo, datos personales y servicios
     * @return profesional almacenada
     */
    public Professional registerProfessional(Map<String, Object> data) {
        String type = (String) data.get("type");
        Objects.requireNonNull(type, "type");
        Professional professional = professionalFactory.createProfessional(type, data);
        return professionalDAO.save(professional);
    }

    /**
     * Persiste la profesional proporcionada sin transformaciones adicionales.
     *
     * @param professional instancia lista para guardarse
     * @return profesional guardada
     */
    public Professional registerProfessional(Professional professional) {
        Objects.requireNonNull(professional, "professional");
        return professionalDAO.save(professional);
    }

    /**
     * Busca profesionales filtrando por zona y categoría solicitadas.
     *
     * @param zone zona geográfica deseada
     * @param category categoría de servicio
     * @return lista filtrada de profesionales
     */
    public List<Professional> searchProfessionals(String zone, String category) {
        return professionalDAO.findAll().stream()
                .filter(pro -> matchesZone(pro, zone))
                .filter(pro -> matchesCategory(pro, category))
                .collect(Collectors.toList());
    }

    /**
     * Lista los servicios publicados por una profesional específica.
     *
     * @param professionalId identificador de la profesional
     * @return servicios registrados
     */
    public List<ServiceComponent> listServices(String professionalId) {
        return new ArrayList<>(serviceDAO.findByProfessionalId(professionalId));
    }

    /**
     * Crea un servicio simple asociado a una profesional validada.
     *
     * @param professionalId id de la profesional propietaria
     * @param name nombre del servicio
     * @param description descripción comercial
     * @param price precio base
     * @param duration duración estimada en minutos
     * @param imageUrls galería de soporte
     * @return servicio persistido
     */
    public ServiceComponent createBasicService(String professionalId,
                                               String name,
                                               String description,
                                               double price,
                                               int duration,
                                               List<String> imageUrls) {
        if (professionalDAO.findById(professionalId) == null) {
            throw new IllegalArgumentException("Professional not found: " + professionalId);
        }
        ServiceComponent service = serviceDirector.constructService(name, description, price, duration, imageUrls);
        return serviceDAO.saveForProfessional(professionalId, service);
    }

    /**
     * Variante abreviada para reservar un servicio sin zona específica.
     *
     * @param clientId cliente que agenda
     * @param professionalId profesional asignada
     * @param serviceId servicio a ejecutar
     * @param dateTime fecha y hora deseada
     * @return reserva confirmada
     */
    public Booking bookService(String clientId,
                               String professionalId,
                               String serviceId,
                               LocalDateTime dateTime) {
        return bookService(clientId, professionalId, serviceId, dateTime, null);
    }

    /**
     * Reserva un servicio tras validar existencia de entidades y cálculo de precio.
     *
     * @param clientId cliente que agenda
     * @param professionalId profesional asignada
     * @param serviceId servicio solicitado
     * @param dateTime fecha/hora solicitada
     * @param zone zona opcional para cobertura
     * @return reserva persistida y notificada
     */
    public Booking bookService(String clientId,
                               String professionalId,
                               String serviceId,
                               LocalDateTime dateTime,
                               String zone) {
        ServiceComponent service = serviceDAO.findById(serviceId);
        Client client = clientDAO.findById(clientId);
        Professional professional = professionalDAO.findById(professionalId);

        if (service == null) {
            throw new IllegalArgumentException("Service not found: " + serviceId);
        }
        if (client == null) {
            throw new IllegalArgumentException("Client not found: " + clientId);
        }
        if (professional == null) {
            throw new IllegalArgumentException("Professional not found: " + professionalId);
        }

        pricingStrategy.calculatePrice(service.getPrice(), client, service);

        BookingRequest request = new BookingRequest();
        request.setClientId(clientId);
        request.setProfessionalId(professionalId);
        request.setServiceId(serviceId);
        request.setDateTime(dateTime);
        request.setZone(zone);

        Booking booking = bookingService.book(request);
        booking.attach(new ClientNotificationObserver(client));
        booking.attach(new ProfessionalNotificationObserver(professional));
        Booking persisted = bookingDAO.save(booking);
        // El cálculo de precios se mantiene para integraciones futuras (facturación, etc.)
        return persisted;
    }

    /**
     * Cancela una reserva existente empleando el patrón Command.
     *
     * @param bookingId identificador de la reserva a cancelar
     */
    public void cancelBooking(String bookingId) {
        CancelBookingCommand command = new CancelBookingCommand(agendaSingleton, bookingId);
        commandInvoker.setCommand(command);
        commandInvoker.executeCommand();
        if (!command.isCancelled()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }
        bookingDAO.delete(bookingId);
    }

    /**
     * Crea una reseña aplicando la protección del proxy anti-duplicados.
     *
     * @param bookingId reserva evaluada
     * @param rating calificación de 1-5
     * @param text comentario opcional
     * @return reseña persistida
     */
    public Review addReview(String bookingId, int rating, String text) {
        return reviewGuardProxy.createReview(bookingId, rating, text);
    }

    /**
     * Registra una fotografía y la marca como pública únicamente con consentimiento.
     *
     * @param bookingId reserva asociada
     * @param url ubicación de origen de la foto
     * @param isPublic indicador de publicación solicitada
     */
    public void uploadPhoto(String bookingId, String url, boolean isPublic) {
        consentProxy.addPhoto(bookingId, url, isPublic);
    }

    /**
     * Concede el consentimiento que habilita la publicación de fotos.
     *
     * @param bookingId reserva autorizada
     */
    public void grantPhotoConsent(String bookingId) {
        consentProxy.addConsent(bookingId);
    }

    /**
     * Calcula el promedio de calificaciones de una profesional.
     *
     * @param professionalId profesional evaluada
     * @return promedio numérico o 0.0 si no hay reseñas
     */
    public double getProfessionalAverageRating(String professionalId) {
        return reviewDAO.findByProfessionalId(professionalId).stream()
                .mapToInt(review -> review.getRating().getValue())
                .average()
                .orElse(0.0);
    }

    /**
     * Construye un historial de servicios con fotos aprobadas para la profesional.
     *
     * @param professionalId profesional consultada
     * @return lista de historiales enriquecidos
     */
    public List<ServiceHistory> viewProfessionalHistory(String professionalId) {
        List<Booking> bookings = bookingDAO.findByProfessionalId(professionalId);
        Professional professional = professionalDAO.findById(professionalId);
        if (professional == null) {
            return Collections.emptyList();
        }
        List<Photo> professionalPhotos = consentProxy.listByProfessional(professionalId);
        List<ServiceHistory> histories = new ArrayList<>();
        for (Booking booking : bookings) {
            Client client = clientDAO.findById(booking.getClientId());
            if (client == null) {
                continue;
            }
            ServiceComponent service = serviceDAO.findById(booking.getServiceId());
            ServiceHistory history = new ServiceHistory(booking, client, professional, service, booking.getDateTime());
            List<Photo> photos = professionalPhotos.stream()
                    .filter(photo -> Objects.equals(photo.getBookingId(), booking.getId()))
                    .collect(Collectors.toList());
            photos.forEach(history::addPhoto);
            histories.add(history);
        }
        return histories;
    }

    /**
     * Evalúa si la profesional ofrece la categoría solicitada.
     */
    private boolean matchesCategory(Professional professional, String category) {
        if (category == null || category.isBlank()) {
            return true;
        }
        String normalized = category.trim().toLowerCase(Locale.ROOT);

        String typeName = professional.getClass().getSimpleName();
        if (typeName != null && typeName.toLowerCase(Locale.ROOT).contains(normalized)) {
            return true;
        }

        if (professional.getServicesOffered() == null) {
            return false;
        }
        return professional.getServicesOffered().stream().anyMatch(service -> {
            if (service instanceof ServiceLeaf leaf && leaf.getCategory() != null
                && leaf.getCategory().getName() != null
                && leaf.getCategory().getName().toLowerCase(Locale.ROOT).contains(normalized)) {
                return true;
            }
            String serviceName = service.getName();
            return serviceName != null && serviceName.toLowerCase(Locale.ROOT).contains(normalized);
        });
    }

    /**
     * Determina si la profesional cubre la zona indicada a través del proxy.
     */
    private boolean matchesZone(Professional professional, String zone) {
        if (zone == null || zone.isBlank()) {
            return true;
        }
        CoverageProxy proxy = new CoverageProxy(professional);
        return proxy.isAvailable(zone);
    }
}
