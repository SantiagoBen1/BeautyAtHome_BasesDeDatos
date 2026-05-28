package com.beautyathome.controllers;

import com.beautyathome.entities.BookingEntity;
import com.beautyathome.repositories.JpaBookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final JpaBookingRepository bookingRepository;

    public BookingController(JpaBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // 1. GET (Obtener todas las reservas) - HTTP 200 OK
    @GetMapping
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        return ResponseEntity.ok(bookingRepository.findAll());
    }

    // 2. GET (Obtener reserva por ID) - HTTP 200 OK o HTTP 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST (Crear nueva reserva) - HTTP 201 Created
    @PostMapping
    public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingEntity newBooking) {
        // La restricción DEFAULT de la BD se encargará del estado si no viene en el
        // JSON
        BookingEntity savedBooking = bookingRepository.save(newBooking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }

    // 4. PUT (Actualizar reserva existente) - HTTP 200 OK o HTTP 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<BookingEntity> updateBooking(
            @PathVariable Integer id,
            @RequestBody BookingEntity bookingDetails) {

        return bookingRepository.findById(id).map(booking -> {
            // Se actualiza los campos que el cliente o admin pueden modificar
            if (bookingDetails.getDatetimeStart() != null) {
                booking.setDatetimeStart(bookingDetails.getDatetimeStart());
            }
            if (bookingDetails.getStatus() != null) {
                booking.setStatus(bookingDetails.getStatus());
            }
            if (bookingDetails.getTotalPrice() != null) {
                booking.setTotalPrice(bookingDetails.getTotalPrice());
            }

            return ResponseEntity.ok(bookingRepository.save(booking));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE (Eliminar reserva) - HTTP 204 No Content o HTTP 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Retorna 204 indicando éxito sin cuerpo
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si no existe
    }
}