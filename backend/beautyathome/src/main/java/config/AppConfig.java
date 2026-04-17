package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.booking.BookingService;
import application.booking.validation.AvailabilityValidationHandler;
import application.booking.validation.BookingRequestHandler;
import application.booking.validation.ConsentValidationHandler;
import application.booking.validation.CoverageValidationHandler;
import application.booking.validation.PaymentValidationHandler;
import application.facade.BeautyAtHomeFacade;
import domain.booking.AgendaSingleton;
import domain.booking.command.CommandInvoker;
import domain.pricing.PricingStrategy;
import domain.pricing.StandardPricingStrategy;
import domain.professional.factory.ConcreteProfessionalFactory;
import domain.professional.factory.ProfessionalAbstractFactory;
import domain.service.builder.BasicServiceBuilder;
import domain.service.builder.ServiceBuilder;
import domain.service.builder.ServiceDirector;
import infrastructure.media.ConsentProxy;
import infrastructure.media.PhotoGallery;
import infrastructure.media.StorageAdapter;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;
import infrastructure.persistence.dao.ServiceDAO;
import infrastructure.persistence.dao.memory.InMemoryBookingDAO;
import infrastructure.persistence.dao.memory.InMemoryClientDAO;
import infrastructure.persistence.dao.memory.InMemoryProfessionalDAO;
import infrastructure.persistence.dao.memory.InMemoryReviewDAO;
import infrastructure.persistence.dao.memory.InMemoryServiceDAO;
import infrastructure.proxy.ReviewGuardProxy;
import infrastructure.proxy.ReviewService;

@Configuration
public class AppConfig {

    @Bean
    public ClientDAO clientDAO() {
        return new InMemoryClientDAO();
    }

    @Bean
    public ProfessionalDAO professionalDAO() {
        return new InMemoryProfessionalDAO();
    }

    @Bean
    public ServiceDAO serviceDAO() {
        return new InMemoryServiceDAO();
    }

    @Bean
    public BookingDAO bookingDAO() {
        return new InMemoryBookingDAO();
    }

    @Bean
    public ReviewDAO reviewDAO() {
        return new InMemoryReviewDAO();
    }

    @Bean
    public AgendaSingleton agendaSingleton() {
        return AgendaSingleton.getInstance();
    }

    @Bean
    public CommandInvoker commandInvoker() {
        return new CommandInvoker();
    }

    @Bean
    public CoverageValidationHandler coverageValidationHandler(ProfessionalDAO professionalDAO) {
        return new CoverageValidationHandler(professionalDAO);
    }

    @Bean
    public AvailabilityValidationHandler availabilityValidationHandler(AgendaSingleton agendaSingleton) {
        return new AvailabilityValidationHandler(agendaSingleton, 60);
    }

    @Bean
    public ConsentValidationHandler consentValidationHandler(ClientDAO clientDAO) {
        return new ConsentValidationHandler(clientDAO);
    }

    @Bean
    public PaymentValidationHandler paymentValidationHandler(ClientDAO clientDAO) {
        return new PaymentValidationHandler(clientDAO);
    }

    @Bean
    public BookingRequestHandler bookingValidationChain(CoverageValidationHandler coverage,
                                                        AvailabilityValidationHandler availability,
                                                        ConsentValidationHandler consent,
                                                        PaymentValidationHandler payment) {
        coverage.setNext(availability).setNext(consent).setNext(payment);
        return coverage;
    }

    @Bean
    public BookingService bookingService(BookingRequestHandler bookingValidationChain,
                                         AgendaSingleton agendaSingleton,
                                         CommandInvoker commandInvoker) {
        return new BookingService(bookingValidationChain, agendaSingleton, commandInvoker);
    }

    @Bean
    public PricingStrategy pricingStrategy() {
        return new StandardPricingStrategy();
    }

    @Bean
    public ProfessionalAbstractFactory professionalFactory() {
        return new ConcreteProfessionalFactory();
    }

    @Bean
    public ServiceBuilder serviceBuilder() {
        return new BasicServiceBuilder();
    }

    @Bean
    public ServiceDirector serviceDirector(ServiceBuilder serviceBuilder) {
        return new ServiceDirector(serviceBuilder);
    }

    @Bean
    public StorageAdapter storageAdapter() {
        return new StorageAdapter();
    }

    @Bean
    public PhotoGallery photoGallery(StorageAdapter storageAdapter, BookingDAO bookingDAO) {
        return new PhotoGallery(storageAdapter, bookingDAO);
    }

    @Bean
    public ConsentProxy consentProxy(PhotoGallery photoGallery) {
        return new ConsentProxy(photoGallery);
    }

    @Bean
    public ReviewService reviewService(BookingDAO bookingDAO,
                                       ClientDAO clientDAO,
                                       ProfessionalDAO professionalDAO,
                                       ReviewDAO reviewDAO) {
        return new ReviewService(bookingDAO, clientDAO, professionalDAO, reviewDAO);
    }

    @Bean
    public ReviewGuardProxy reviewGuardProxy(ReviewService reviewService) {
        return new ReviewGuardProxy(reviewService);
    }

    @Bean
    public BeautyAtHomeFacade beautyAtHomeFacade(ClientDAO clientDAO,
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
        return new BeautyAtHomeFacade(clientDAO,
                professionalDAO,
                serviceDAO,
                bookingDAO,
                reviewDAO,
                bookingService,
                pricingStrategy,
                professionalFactory,
                serviceDirector,
                reviewGuardProxy,
                consentProxy,
                commandInvoker,
                agendaSingleton);
    }
}
