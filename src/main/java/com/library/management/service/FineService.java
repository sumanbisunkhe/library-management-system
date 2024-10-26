package com.library.management.service;

import com.library.management.dto.FineDto;

import java.time.LocalDateTime;
import java.util.List;

public interface FineService {
    FineDto createFine(Long borrowId);
    FineDto getFineById(Long id);
    List<FineDto> getAllFines();
    List<FineDto> getFinesByBorrowId(Long borrowId);
    FineDto payFine(Long id);
    void deleteFine(Long id);
    Double calculateFineAmount(FineDto fineDto);
    LocalDateTime getDueDateByBorrowId(Long borrowId);
}
