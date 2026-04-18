package com.beautyathome.application.facade;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.beautyathome.application.booking.BookingRequest;
import com.beautyathome.application.booking.BookingService;
import com.beautyathome.domain.booking.AgendaSingleton;
import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.command.CancelBookingCommand;
import com.beautyathome.domain.booking.command.CommandInvoker;
import com.beautyathome.domain.booking.history.ServiceHistory;
import com.beautyathome.domain.booking.observer.ClientNotificationObserver;
import com.beautyathome.domain.booking.observer.ProfessionalNotificationObserver;
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.domain.pricing.PricingStrategy;
import com.beautyathome.domain.professional.Professional;
import com.beautyathome.domain.professional.factory.ProfessionalAbstractFactory;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;
import com.beautyathome.domain.review.Review;
import com.beautyathome.domain.review.port.out.ReviewRepositoryPort;
import com.beautyathome.domain.service.ServiceComponent;
import com.beautyathome.domain.service.ServiceLeaf;
import com.beautyathome.domain.service.builder.ServiceDirector;
import com.beautyathome.domain.service.image.Photo;
import com.beautyathome.domain.service.port.out.ServiceRepositoryPort;
import com.beautyathome.infrastructure.adapter.out.media.ConsentProxy;
import com.beautyathome.infrastructure.proxy.CoverageProxy;
import com.beautyathome.infrastructure.proxy.ReviewGuardProxy;

@Service
public class BeautyAtHomeFacade {
    private final ClientRepositoryPort clientRepositoryPort;
    private final ProfessionalRepositoryPort professionalRepositoryPort;
    private final BookingRepositoryPort bookingRepositoryPort;
    private final ServiceRepositoryPort serviceRepositoryPort;
    private final ReviewRepositoryPort reviewRepositoryPort;
    private final ProfessionalAbstractFactory professionalFactory;
    private final ServiceDirector serviceDirector;
    private final PricingStrategy pricingStrategy;
    private final BookingService bookingService;
    private final AgendaSingleton agendaSingleton;
    private final CommandInvoker commandInvoker;
    private final ReviewGuardProxy reviewGuardProxy;
    private final ConsentProxy consentProxy;

    public BeautyAtHomeFacade(ClientRepositoryPort clientRepositoryPort, 
                              ProfessionalRepositoryPort professionalRepositoryPort,
                              BookingRepositoryPort bookingRepositoryPort, 
                              ServiceRepositoryPort serviceRepositoryPort,
                              ReviewRepositoryPort reviewRepositoryPort,
                              ProfessionalAbstractFactory professionalFactory,
                              ServiceDirector serviceDirector,
                              PricingStrategy pricingStrategy,
                              BookingService bookingService,
                              CommandInvoker commandInvoker,
                              ReviewGuardProxy reviewGuardProxy,
                              ConsentProxy consentProxy) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.professionalRepositoryPort = professionalRepositoryPort;
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.serviceRepositoryPort = serviceRepositoryPort;
        this.reviewRepositoryPort = reviewRepositoryPort;
        this.professionalFactory = professionalFactory;
        this.serviceDirector = serviceDirector;
        this.pricingStrategy = pricingStrategy;
        this.bookingService = bookingService;
        this.commandInvoker = commandInvoker;
        this.reviewGuardProxy = reviewGuardProxy;
        this.consentProxy = consentProxy;
        this.agendaSingleton = AgendaSingleton.getInstance();
    }

    /**
     * Registra un cliente a partir de datos dinÃ¡micos (p. ej. JSON de API).
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
     * @return cliente persistido con id vÃ¡lido
     */
    public Client registerClient(Client client) {
        Objects.requireNonNull(client, "client");
        Client normalized = client.getId() == null || client.getId().isBlank()
                ? new Client(UUID.randomUUID().toString(), client.getName(), client.getEmail())
                : client;
        return clientRepositoryPort.save(normalized);
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
        return professionalRepositoryPort.save(professional);
    }

    /**
     * Persiste la profesional proporcionada sin transformaciones adicionales.
     *
     * @param professional instancia lista para guardarse
     * @return profesional guardada
     */
    public Professional registerProfessional(Professional professional) {
        Objects.requireNonNull(professional, "professional");
        return professionalRepositoryPort.save(professional);
    }

    /**
     * Busca profesionales filtrando por zona y categorÃ­a solicitadas.
     *
     * @param zone zona geogrÃ¡fica deseada
     * @param category categorÃ­a de servicio
     * @return lista filtrada de profesionales
     */
    public List<Professional> searchProfessionals(String zone, String category) {
        return professionalRepositoryPort.findAll().stream()
                .filter(pro -> matchesZone(pro, zone))
                .filter(pro -> matchesCategory(pro, category))
                .collect(Collectors.toList());
    }

    /**
     * Lista los servicios publicados por una profesional especÃ­fica.
     *
     * @param professionalId identificador de la profesional
     * @return servicios registrados
     */
    public List<ServiceComponent> listServices(String professionalId) {
        return new ArrayList<>(serviceRepositoryPort.findByProfessionalId(professionalId));
    }

    /**
     * Crea un servicio simple asociado a una profesional validada.
     *
     * @param professionalId id de la profesional propietaria
     * @param name nombre del servicio
     * @param description descripciÃ³n comercial
     * @param price precio base
     * @param duration duraciÃ³n estimada en minutos
     * @param imageUrls galerÃ­a de soporte
     * @return servicio persistido
     */
    public ServiceComponent createBasicService(String professionalId,
                                               String name,
                                               String description,
                                               double price,
                                               int duration,
                                               List<String> imageUrls) {
        if (professionalRepositoryPort.findById(professionalId).orElse(null) == null) {
            throw new IllegalArgumentException("Professional not found: " + professionalId);
        }
        ServiceComponent service = serviceDirector.constructService(name, description, price, duration, imageUrls);
        return serviceRepositoryPort.saveForProfessional(professionalId, service);
    }

    /**
     * Variante abreviada para reservar un servicio sin zona especÃ­fica.
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
     * Reserva un servicio tras validar existencia de entidades y cÃ¡lculo de precio.
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
        ServiceComponent service = serviceRepositoryPort.findById(serviceId).orElse(null);
        Client client = clientRepositoryPort.findById(clientId).orElse(null);
        Professional professional = professionalRepositoryPort.findById(professionalId).orElse(null);

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
        Booking persisted = bookingRepositoryPort.save(booking);
        // El cÃ¡lculo de precios se mantiene para integraciones futuras (facturaciÃ³n, etc.)
        return persisted;
    }

    /**
     * Cancela una reserva existente empleando el patrÃ³n Command.
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
        bookingRepositoryPort.delete(bookingId);
    }

    /**
     * Crea una reseÃ±a aplicando la protecciÃ³n del proxy anti-duplicados.
     *
     * @param bookingId reserva evaluada
     * @param rating calificaciÃ³n de 1-5
     * @param text comentario opcional
     * @return reseÃ±a persistida
     */
    public Review addReview(String bookingId, int rating, String text) {
        return reviewGuardProxy.createReview(bookingId, rating, text);
    }

    /**
     * Registra una fotografÃ­a y la marca como pÃºblica Ãºnicamente con consentimiento.
     *
     * @param bookingId reserva asociada
     * @param url ubicaciÃ³n de origen de la foto
     * @param isPublic indicador de publicaciÃ³n solicitada
     */
    public void uploadPhoto(String bookingId, String url, boolean isPublic) {
        consentProxy.addPhoto(bookingId, url, isPublic);
    }

    /**
     * Concede el consentimiento que habilita la publicaciÃ³n de fotos.
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
     * @return promedio numÃ©rico o 0.0 si no hay reseÃ±as
     */
    public double getProfessionalAverageRating(String professionalId) {
        return reviewRepositoryPort.findByProfessionalId(professionalId).stream()
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
        List<Booking> bookings = bookingRepositoryPort.findByProfessionalId(professionalId);
        Professional professional = professionalRepositoryPort.findById(professionalId).orElse(null);
        if (professional == null) {
            return Collections.emptyList();
        }
        List<Photo> professionalPhotos = consentProxy.listByProfessional(professionalId);
        List<ServiceHistory> histories = new ArrayList<>();
        for (Booking booking : bookings) {
            Client client = clientRepositoryPort.findById(booking.getClientId()).orElse(null);
            if (client == null) {
                continue;
            }
            ServiceComponent service = serviceRepositoryPort.findById(booking.getServiceId()).orElse(null);
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
     * EvalÃºa si la profesional ofrece la categorÃ­a solicitada.
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
     * Determina si la profesional cubre la zona indicada a travÃ©s del proxy.
     */
    private boolean matchesZone(Professional professional, String zone) {
        if (zone == null || zone.isBlank()) {
            return true;
        }
        CoverageProxy proxy = new CoverageProxy(professional);
        return proxy.isAvailable(zone);
    }
}
