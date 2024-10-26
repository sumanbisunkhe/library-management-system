package com.library.management.service;

import com.library.management.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(ReservationDto reservationDto);
    ReservationDto getReservationById(Long id);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsByUserId(Long userId);
    List<ReservationDto> getReservationsByBookId(Long bookId);
    void cancelReservation(Long id);
    ReservationDto confirmReservation(Long id);
}
