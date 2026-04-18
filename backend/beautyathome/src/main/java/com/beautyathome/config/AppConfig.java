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
import com.beautyathome.domain.booking.port.out.BookingRepositoryPort;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;
import com.beautyathome.domain.professional.port.out.ProfessionalRepositoryPort;
import com.beautyathome.domain.review.port.out.ReviewRepositoryPort;
import com.beautyathome.domain.service.port.out.ServiceRepositoryPort;
import com.beautyathome.infrastructure.adapter.out.persistence.adapter.BookingPersistenceAdapter;
import com.beautyathome.infrastructure.adapter.out.persistence.adapter.ClientPersistenceAdapter;
import com.beautyathome.infrastructure.adapter.out.persistence.adapter.ProfessionalPersistenceAdapter;
import com.beautyathome.infrastructure.adapter.out.persistence.adapter.ReviewPersistenceAdapter;
import com.beautyathome.infrastructure.adapter.out.persistence.adapter.ServicePersistenceAdapter;
import com.beautyathome.infrastructure.proxy.ReviewGuardProxy;
import com.beautyathome.infrastructure.proxy.ReviewService;

@Configuration
public class AppConfig {

    @Bean
    public ClientRepositoryPort clientRepositoryPort(ClientPersistenceAdapter clientPersistenceAdapter) {
        return clientPersistenceAdapter;
    }

    @Bean
    public ProfessionalRepositoryPort professionalRepositoryPort(ProfessionalPersistenceAdapter professionalPersistenceAdapter) {
        return professionalPersistenceAdapter;
    }

    @Bean
    public ServiceRepositoryPort serviceRepositoryPort(ServicePersistenceAdapter servicePersistenceAdapter) {
        return servicePersistenceAdapter;
    }

    @Bean
    public BookingRepositoryPort bookingRepositoryPort(BookingPersistenceAdapter bookingPersistenceAdapter) {
        return bookingPersistenceAdapter;
    }

    @Bean
    public ReviewRepositoryPort reviewRepositoryPort(ReviewPersistenceAdapter reviewPersistenceAdapter) {
        return reviewPersistenceAdapter;
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
    public CoverageValidationHandler coverageValidationHandler(ProfessionalRepositoryPort professionalRepositoryPort) {
        return new CoverageValidationHandler(professionalRepositoryPort);
    }

    @Bean
    public AvailabilityValidationHandler availabilityValidationHandler(AgendaSingleton agendaSingleton) {
        return new AvailabilityValidationHandler(agendaSingleton, 60);
    }

    @Bean
    public ConsentValidationHandler consentValidationHandler(ClientRepositoryPort clientRepositoryPort) {
        return new ConsentValidationHandler(clientRepositoryPort);
    }

    @Bean
    public PaymentValidationHandler paymentValidationHandler(ClientRepositoryPort clientRepositoryPort) {
        return new PaymentValidationHandler(clientRepositoryPort);
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
    public PhotoGallery photoGallery(StorageAdapter storageAdapter, BookingRepositoryPort bookingRepositoryPort) {
        return new PhotoGallery(storageAdapter, bookingRepositoryPort);
    }

    @Bean
    public ConsentProxy consentProxy(PhotoGallery photoGallery) {
        return new ConsentProxy(photoGallery);
    }

    @Bean
    public ReviewService reviewService(BookingRepositoryPort bookingRepositoryPort,
                                       ClientRepositoryPort clientRepositoryPort,
                                       ProfessionalRepositoryPort professionalRepositoryPort,
                                       ReviewRepositoryPort reviewRepositoryPort) {
        return new ReviewService(bookingRepositoryPort, clientRepositoryPort, professionalRepositoryPort, reviewRepositoryPort);
    }

    @Bean
    public ReviewGuardProxy reviewGuardProxy(ReviewService reviewService) {
        return new ReviewGuardProxy(reviewService);
    }

    @Bean
    public BeautyAtHomeFacade beautyAtHomeFacade(ClientRepositoryPort clientRepositoryPort,
                                                 ProfessionalRepositoryPort professionalRepositoryPort,
                                                 ServiceRepositoryPort serviceRepositoryPort,
                                                 BookingRepositoryPort bookingRepositoryPort,
                                                 ReviewRepositoryPort reviewRepositoryPort,
                                                 BookingService bookingService,
                                                 PricingStrategy pricingStrategy,
                                                 ProfessionalAbstractFactory professionalFactory,
                                                 ServiceDirector serviceDirector,
                                                 ReviewGuardProxy reviewGuardProxy,
                                                 ConsentProxy consentProxy,
                                                 CommandInvoker commandInvoker,
                                                 AgendaSingleton agendaSingleton) {
        return new BeautyAtHomeFacade(clientRepositoryPort,
                professionalRepositoryPort,
                serviceRepositoryPort,
                bookingRepositoryPort,
                reviewRepositoryPort,
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