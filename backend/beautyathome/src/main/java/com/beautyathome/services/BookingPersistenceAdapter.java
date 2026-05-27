package com.beautyathome.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beautyathome.domain.booking.Booking;
import com.beautyathome.domain.booking.BookingBuilder;
import com.beautyathome.entities.BookingEntity;
import com.beautyathome.entities.ClientEntity;
import com.beautyathome.entities.ProfessionalEntity;
import com.beautyathome.entities.ServiceEntity;
import com.beautyathome.repositories.BookingRepository;
import com.beautyathome.repositories.JpaBookingRepository;

@Component
@Transactional(readOnly = true)
public class BookingPersistenceAdapter implements BookingRepository {

    private final JpaBookingRepository jpaRepository;

    public BookingPersistenceAdapter(JpaBookingRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public Booking save(Booking booking) {
        BookingEntity entity = toEntity(booking);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Booking> findById(String id) {
        try {
            return jpaRepository.findById(Integer.parseInt(id)).map(this::toDomain);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Booking> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByProfessionalId(String professionalId) {
        try {
            return jpaRepository.findByProfessionalId(Integer.parseInt(professionalId)).stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    @Override
    public List<Booking> findByClientId(String clientId) {
        try {
            return jpaRepository.findByClientId(Integer.parseInt(clientId)).stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            jpaRepository.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException e) {}
    }

    private BookingEntity toEntity(Booking domain) {
        BookingEntity entity = new BookingEntity();
        if (domain.getId() != null && !domain.getId().isEmpty()) {
            try {
                entity.setId(Integer.parseInt(domain.getId()));
            } catch (NumberFormatException e) {}
        }
        
        ClientEntity client = new ClientEntity();
        try {
            client.setId(Integer.parseInt(domain.getClientId()));
        } catch (Exception e) {}
        entity.setClient(client);

        ProfessionalEntity professional = new ProfessionalEntity();
        try {
            professional.setId(Integer.parseInt(domain.getProfessionalId()));
        } catch (Exception e) {}
        entity.setProfessional(professional);
        
        entity.setDatetimeStart(domain.getDateTime());
        entity.setDatetimeEnd(domain.getDateTime().plusHours(1)); // Dummy end time
        entity.setTotalPrice(100.0); // Dummy price
        
        List<ServiceEntity> services = new ArrayList<>();
        for (String sId : domain.getServiceIds()) {
            try {
                ServiceEntity s = new ServiceEntity();
                s.setId(Integer.parseInt(sId));
                services.add(s);
            } catch (Exception e) {}
        }
        entity.setServices(services);
        
        if (domain.getStatusName() != null) {
            String status = domain.getStatusName().toLowerCase();
            switch (status) {
                case "pending": entity.setStatus("pendiente"); break;
                case "confirmed": entity.setStatus("confirmado"); break;
                case "completed": entity.setStatus("completado"); break;
                case "cancelled": entity.setStatus("cancelado"); break;
                default: entity.setStatus("pendiente"); break;
            }
        } else {
            entity.setStatus("pendiente");
        }
        
        return entity;
    }

    private Booking toDomain(BookingEntity entity) {
        List<String> serviceIds = entity.getServices().stream()
            .map(s -> String.valueOf(s.getId()))
            .collect(Collectors.toList());

        Booking booking = new BookingBuilder()
            .withId(String.valueOf(entity.getId()))
            .withClient(String.valueOf(entity.getClient().getId()))
            .withProfessional(String.valueOf(entity.getProfessional().getId()))
            .withServices(serviceIds)
            .withDate(entity.getDatetimeStart())
            .build();
            
        if ("completado".equalsIgnoreCase(entity.getStatus())) {
            booking.setState(new com.beautyathome.domain.booking.state.CompletedState());
        } else if ("cancelado".equalsIgnoreCase(entity.getStatus())) {
            booking.setState(new com.beautyathome.domain.booking.state.CancelledState());
        }
        
        return booking;
    }
}