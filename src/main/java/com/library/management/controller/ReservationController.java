package com.library.management.controller;

import com.library.management.dto.ReservationDto;
import com.library.management.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Create Reservation
    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDto reservationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation errors and return them
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        ReservationDto createdReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("Reservation created successfully with ID: %d.", createdReservation.getId()));
    }

    // Get Reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        try {
            ReservationDto reservationDto = reservationService.getReservationById(id);
            return ResponseEntity.ok(reservationDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Reservation not found with ID: %d. Please check the ID and try again.", id));
        }
    }

    // Get All Reservations
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Get Reservations by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDto> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    // Get Reservations by Book ID
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByBookId(@PathVariable Long bookId) {
        List<ReservationDto> reservations = reservationService.getReservationsByBookId(bookId);
        return ResponseEntity.ok(reservations);
    }
    // Confirm Reservation
    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long id) {
        try {
            ReservationDto confirmedReservation = reservationService.confirmReservation(id);
            return ResponseEntity.ok(
                    String.format("Reservation with ID: %d confirmed successfully.", confirmedReservation.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Reservation not found with ID: %d. Unable to confirm non-existent reservation.", id));
        }
    }

    // Cancel Reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.ok(String.format("Reservation with ID: %d canceled successfully.", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Reservation not found with ID: %d. Unable to cancel non-existent reservation.", id));
        }
    }
}
