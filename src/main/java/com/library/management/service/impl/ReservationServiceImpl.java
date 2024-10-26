package com.library.management.service.impl;

import com.library.management.dto.ReservationDto;
import com.library.management.entities.Book;
import com.library.management.entities.Reservation;
import com.library.management.entities.User;

import com.library.management.repo.BookRepo;
import com.library.management.repo.ReservationRepo;
import com.library.management.repo.UserRepo;
import com.library.management.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        // Convert DTO to Entity
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        // Optional: Check if user and book exist before saving
        User user = userRepo.findById(reservationDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(reservationDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Set user and book
        reservation.setUser(user);
        reservation.setBook(book);

        // Set reservedAt to now and isConfirmed to false
        reservation.setReservedAt(LocalDateTime.now());
        reservation.setIsConfirmed(false);

        // Set expirationAt to some defined duration (e.g., 21 days from now)
        reservation.setExpirationAt(LocalDateTime.now().plusDays(21));

        // Save the reservation
        reservation = reservationRepo.save(reservation);

        // Convert back to DTO
        return modelMapper.map(reservation, ReservationDto.class);
    }


    @Override
    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepo.findAll().stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByUserId(Long userId) {
        return reservationRepo.findByUserId(userId).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByBookId(Long bookId) {
        return reservationRepo.findByBookId(bookId).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ReservationDto confirmReservation(Long id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setIsConfirmed(true);
        reservation = reservationRepo.save(reservation);

        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public void cancelReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepo.findById(id);
        if (reservationOptional.isPresent()) {
            reservationRepo.delete(reservationOptional.get());
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
}
