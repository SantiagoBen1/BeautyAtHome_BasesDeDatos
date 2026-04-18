package com.beautyathome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beautyathome.application.booking.BookingService;
import com.beautyathome.application.booking.validation.AvailabilityValidationHandler;
import com.beautyathome.application.booking.validation.BookingRequestHandler;
import com.beautyathome.application.booking.validation.ConsentValidationHandler;
import com.beautyathome.application.booking.validation.CoverageValidationHandler;
import com.beautyathome.application.booking.validation.PaymentValidationHandler;
import com.beautyathome.application.facade.BeautyAtHomeFacade;
import com.beautyathome.domain.booking.AgendaSingleton;
import com.beautyathome.domain.booking.command.CommandInvoker;
import com.beautyathome.domain.pricing.PricingStrategy;
import com.beautyathome.domain.pricing.StandardPricingStrategy;
import com.beautyathome.domain.professional.factory.ConcreteProfessionalFactory;
import com.beautyathome.domain.professional.factory.ProfessionalAbstractFactory;
import com.beautyathome.domain.service.builder.BasicServiceBuilder;
import com.beautyathome.domain.service.builder.ServiceBuilder;
import com.beautyathome.domain.service.builder.ServiceDirector;
import com.beautyathome.infrastructure.adapter.out.media.ConsentProxy;
import com.beautyathome.infrastructure.adapter.out.media.PhotoGallery;
import com.beautyathome.infrastructure.adapter.out.media.StorageAdapter;
import infrastructure.persistence.dao.BookingDAO;
import infrastructure.persistence.dao.ClientDAO;
import infrastructure.persistence.dao.ProfessionalDAO;
import infrastructure.persistence.dao.ReviewDAO;
import infrastructure.persistence.dao.ServiceDAO;
import infrastructure.persistence.dao.postgres.PostgresBookingDAO;
import infrastructure.persistence.dao.postgres.PostgresClientDAO;
import infrastructure.persistence.dao.postgres.PostgresProfessionalDAO;
import infrastructure.persistence.dao.postgres.PostgresReviewDAO;
import infrastructure.persistence.dao.postgres.PostgresServiceDAO;
import com.beautyathome.infrastructure.proxy.ReviewGuardProxy;
import com.beautyathome.infrastructure.proxy.ReviewService;

@Configuration
public class AppConfig {

    @Bean
    public ClientDAO clientDAO(PostgresClientDAO postgresClientDAO) {
        return postgresClientDAO;
    }

    @Bean
    public ProfessionalDAO professionalDAO(PostgresProfessionalDAO postgresProfessionalDAO) {
        return postgresProfessionalDAO;
    }

    @Bean
    public ServiceDAO serviceDAO(PostgresServiceDAO postgresServiceDAO) {
        return postgresServiceDAO;
    }

    @Bean
    public BookingDAO bookingDAO(PostgresBookingDAO postgresBookingDAO) {
        return postgresBookingDAO;
    }

    @Bean
    public ReviewDAO reviewDAO(PostgresReviewDAO postgresReviewDAO) {
        return postgresReviewDAO;
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