package config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import application.facade.BeautyAtHomeFacade;
import domain.booking.Booking;
import domain.client.Client;
import domain.professional.Professional;
import domain.service.ServiceComponent;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ServiceDAO;

/**
 * Seeds the in-memory DAOs with sample data so the MVC views display meaningful
 * information on startup. It creates five clients, five professionals, their
 * services, five bookings, sample reviews, and photo consent to exercise the
 * full application workflow.
 */
@Component
public class SampleDataInitializer implements CommandLineRunner {

    private static final int[] RATING_PATTERN = new int[]{5, 4, 5, 3, 5, 4, 5, 4};
    private static final List<String> REVIEW_LIBRARY = List.of(
            "Color impecable, cuidan cada detalle desde la higiene hasta el styling final.",
            "La experiencia se siente premium desde el kit de productos hasta la ambientación.",
            "Puntualidad perfecta y resultados fotográficos listos para redes.",
            "El maquillaje resistió una sesión completa de fotos sin retoques.",
            "La manicura quedó impecable, muy cuidadosos con bioseguridad.",
            "Excelente asesoría para elegir tonos y texturas, quedé fascinada.",
            "El peinado aguantó toda la boda, recibí demasiados elogios.",
            "Servicio a domicilio muy organizado, dejaron todo limpio al finalizar."
    );

    private final BeautyAtHomeFacade facade;
    private final ClientDAO clientDAO;
    private final ProfessionalDAO professionalDAO;
    private final ServiceDAO serviceDAO;

    public SampleDataInitializer(BeautyAtHomeFacade facade,
                                 ClientDAO clientDAO,
                                 ProfessionalDAO professionalDAO,
                                 ServiceDAO serviceDAO) {
        this.facade = facade;
        this.clientDAO = clientDAO;
        this.professionalDAO = professionalDAO;
        this.serviceDAO = serviceDAO;
    }

    @Override
    public void run(String... args) {
        if (!clientDAO.findAll().isEmpty() || !professionalDAO.findAll().isEmpty()) {
            return; // assume data already seeded
        }
        List<Client> clients = seedClients();
        List<Professional> professionals = seedProfessionals();
        seedServices(professionals);
        List<Booking> bookings = seedBookings(clients, professionals);
        diversifyBookingStates(bookings);
        seedReviewsAndPhotos(bookings);
    }

    private List<Client> seedClients() {
        return List.of(
                facade.registerClient(Map.of("id", "cli-001", "name", "Mariana Torres", "email", "mariana@mail.com")),
                facade.registerClient(Map.of("id", "cli-002", "name", "Carlos Gomez", "email", "carlos@mail.com")),
                facade.registerClient(Map.of("id", "cli-003", "name", "Luisa Fernanda", "email", "luisa@mail.com")),
                facade.registerClient(Map.of("id", "cli-004", "name", "Andrea Salas", "email", "andrea@mail.com")),
                facade.registerClient(Map.of("id", "cli-005", "name", "Julian Reyes", "email", "julian@mail.com")),
                facade.registerClient(Map.of("id", "cli-006", "name", "Valeria Prieto", "email", "valeria@mail.com")),
                facade.registerClient(Map.of("id", "cli-007", "name", "Santiago Mejia", "email", "santiago@mail.com")),
                facade.registerClient(Map.of("id", "cli-008", "name", "Paula Rojas", "email", "paula@mail.com"))
        );
    }

    private List<Professional> seedProfessionals() {
        return List.of(
            registerProfessional("pro-001", "hairstylist", "Diana Styling",
                List.of("Chapinero", "Usaquén", "Rosales"), "LuxHair", "https://picsum.photos/seed/luxhairlogo/120/120",
                List.of("Kérastase Blond Absolu", "Dyson Corrale Platinum"),
                "Colorista master especializada en rubios lived-in", "diana"),
            registerProfessional("pro-002", "makeupartist", "Aura Makeup",
                List.of("Suba", "Engativá", "Cedritos"), "GlamPro", "https://picsum.photos/seed/glampro/120/120",
                List.of("Charlotte Tilbury Flawless Filter", "Dior Backstage Airflash"),
                "Makeup artist certificada por academias internacionales", "aura"),
            registerProfessional("pro-003", "manicurist", "Nails by Sofi",
                List.of("Chapinero", "Teusaquillo"), null, null,
                List.of(),
                "Manicurista especializada en nail art minimalista y k-beauty", "sofi"),
            registerProfessional("pro-004", "hairstylist", "Barber Max",
                List.of("Kennedy", "Bosa", "Fontibón"), "UrbanCut", "https://picsum.photos/seed/urbancut/120/120",
                List.of("American Crew Fiber", "GHD Oracle Styler"),
                "Barbero de precisión con enfoque editorial", "max"),
            registerProfessional("pro-005", "makeupartist", "Luna Glam",
                List.of("Fontibón", "Engativá", "Modelia"), "BellezaTotal", "https://picsum.photos/seed/belleza/120/120",
                List.of("NARS Light Reflecting", "Fenty Gloss Bomb Heat"),
                "Experta en maquillaje luminoso para eventos de noche", "luna"),
            registerProfessional("pro-006", "manicurist", "Studio Lila",
                List.of("Chía", "Cajicá", "Usaquén"), "PureHands", "https://picsum.photos/seed/purehands/120/120",
                List.of("OPI GelColor Bio Shield", "Herbivore Coconut Milk Bath"),
                "Spa móvil vegano para manos y pies", "lila")
        );
    }

    private Professional registerProfessional(String id,
                                              String type,
                                              String name,
                                              List<String> coverage,
                                              String brandName,
                                              String brandLogo,
                                              List<String> brandProducts,
                                              String experience,
                                              String photoSeed) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("type", type);
        data.put("name", name);
        data.put("photoUrl", "https://picsum.photos/seed/" + photoSeed + "/380/380");
        data.put("experienceSummary", experience);
        data.put("coverage", coverage);
        if (brandName != null) {
            Map<String, Object> brand = new HashMap<>();
            brand.put("name", brandName);
            brand.put("logo", brandLogo);
            brand.put("products", brandProducts == null ? List.of() : brandProducts);
            data.put("brand", brand);
        }
        data.put("services", List.of());
        return facade.registerProfessional(data);
    }

    private void seedServices(List<Professional> professionals) {
        for (Professional professional : professionals) {
            for (ServiceSeed seed : catalogFor(professional)) {
                facade.createBasicService(
                        professional.getId(),
                        seed.name(),
                        seed.description(),
                        seed.price(),
                        seed.duration(),
                        seed.imageUrls()
                );
            }
        }
    }

    private List<Booking> seedBookings(List<Client> clients, List<Professional> professionals) {
        LocalDateTime baseDate = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        List<Booking> bookings = new ArrayList<>();
        int offset = 0;
        for (Professional professional : professionals) {
            List<ServiceComponent> services = serviceDAO.findByProfessionalId(professional.getId());
            if (services.isEmpty()) {
                continue;
            }
            for (ServiceComponent service : services) {
                Client client = clients.get(offset % clients.size());
                LocalDateTime slot = baseDate.plusDays(offset / 2).plusHours((offset % 3) * 2L);
                bookings.add(createBooking(client, professional, service.getName(), slot));
                offset++;
                if (bookings.size() >= 8) {
                    return bookings;
                }
            }
        }
        return bookings;
    }

    private Booking createBooking(Client client,
                                  Professional professional,
                                  String serviceId,
                                  LocalDateTime dateTime) {
        String zone = professional.getCoverageAreas() != null && !professional.getCoverageAreas().isEmpty()
                ? professional.getCoverageAreas().get(0).getName()
                : "Chapinero";
        return facade.bookService(client.getId(), professional.getId(), serviceId, dateTime, zone);
    }

    private void seedReviewsAndPhotos(List<Booking> bookings) {
        Queue<String> photoSeeds = new LinkedList<>(List.of("glow", "editorial", "studio", "nude", "soft", "lux", "vegan", "urban"));
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            facade.grantPhotoConsent(booking.getId());
            String seed = photoSeeds.isEmpty() ? "gallery" + i : photoSeeds.poll();
            facade.uploadPhoto(booking.getId(), photoUrl(seed, "a"), true);
            facade.uploadPhoto(booking.getId(), photoUrl(seed, "b"), true);
            int rating = RATING_PATTERN[i % RATING_PATTERN.length];
            String reviewText = REVIEW_LIBRARY.get(i % REVIEW_LIBRARY.size());
            facade.addReview(booking.getId(), rating, reviewText);
        }
    }

    private List<ServiceSeed> catalogFor(Professional professional) {
        String simpleName = professional.getClass().getSimpleName().toLowerCase();
        if (simpleName.contains("hair")) {
            return List.of(
                    new ServiceSeed(professional.getName() + " Glow Ritual",
                            "Baño de color, olaplex y styling brillante",
                            220_000,
                            120,
                            imageSet(professional.getId(), "glow")),
                    new ServiceSeed("Corte Visagismo Signature",
                            "Diagnóstico facial + corte personalizado + texturizado",
                            160_000,
                            90,
                            imageSet(professional.getId(), "cut"))
            );
        }
        if (simpleName.contains("makeup")) {
            return List.of(
                    new ServiceSeed("Editorial Skin Finish",
                            "Maquillaje HD con aerógrafo, waterproof",
                            190_000,
                            85,
                            imageSet(professional.getId(), "makeup")),
                    new ServiceSeed("Social Glam Night",
                            "Smokey luminoso con lifting de cejas y labios gloss",
                            210_000,
                            95,
                            imageSet(professional.getId(), "night"))
            );
        }
        return List.of(
                new ServiceSeed("Manicure Biosegura",
                        "Limpieza rusa, nivelación y esmaltado gel",
                        130_000,
                        75,
                        imageSet(professional.getId(), "nails")),
                new ServiceSeed("Spa Lila Deluxe",
                        "Hidratación vegana con masaje relajante",
                        150_000,
                        80,
                        imageSet(professional.getId(), "spa"))
        );
    }

    private List<String> imageSet(String professionalId, String keyword) {
        return List.of(
                "https://picsum.photos/seed/" + professionalId + keyword + "1/640/480",
                "https://picsum.photos/seed/" + professionalId + keyword + "2/640/480"
        );
    }

    private String photoUrl(String seed, String suffix) {
        return "https://picsum.photos/seed/gallery" + seed + suffix + "/820/620";
    }

    private void diversifyBookingStates(List<Booking> bookings) {
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            int pattern = i % 4;
            if (pattern == 0) {
                booking.confirm();
                booking.start();
                booking.complete();
            } else if (pattern == 1) {
                booking.confirm();
            } else if (pattern == 2) {
                booking.confirm();
                booking.start();
            } else {
                booking.cancel();
            }
        }
    }

    private record ServiceSeed(String name, String description, double price, int duration, List<String> imageUrls) { }
}
