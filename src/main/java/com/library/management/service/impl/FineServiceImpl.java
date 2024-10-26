package com.library.management.service.impl;

import com.library.management.dto.FineDto;
import com.library.management.entities.Borrow;
import com.library.management.entities.Fine;
import com.library.management.repo.BorrowRepo;
import com.library.management.repo.FineRepo;
import com.library.management.service.FineService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepo fineRepo;

    @Autowired
    private BorrowRepo borrowRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FineDto createFine(Long borrowId) {
        // Fetch the borrow record from the database
        Borrow borrow = borrowRepo.findById(borrowId)
                .orElseThrow(() -> new EntityNotFoundException("Borrow record not found for ID: " + borrowId));

        // Check if a fine already exists for this borrow
        Fine existingFine = fineRepo.findByBorrowId(borrowId).stream().findFirst().orElse(null);
        if (existingFine != null) {
            // Return the existing fine
            return modelMapper.map(existingFine, FineDto.class);
        }

        // Fetch due date from borrow entity
        LocalDateTime dueDate = borrow.getDueDate();
        LocalDateTime currentDate = LocalDateTime.now();

        // Calculate fine amount only if overdue
        double fineAmount = 0.0;
        if (dueDate.isBefore(currentDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, currentDate);
            fineAmount = daysOverdue * 3.0; // Rs. 3 per day overdue
        }

        // If no overdue amount, no fine is created
        if (fineAmount <= 0) {
            throw new RuntimeException("No fine applicable as there is no overdue.");
        }

        // Create and save the Fine entity
        Fine fine = new Fine();
        fine.setBorrow(borrow);
        fine.setDueDate(dueDate);
        fine.setAmount(fineAmount);
        fine.setCreatedAt(LocalDateTime.now());
        fine.setPaid(false);
        fine = fineRepo.save(fine);

        // Convert to DTO for response
        return modelMapper.map(fine, FineDto.class);
    }


    @Override
    public FineDto getFineById(Long id) {
        Fine fine = fineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found"));
        return modelMapper.map(fine, FineDto.class);
    }

    @Override
    public List<FineDto> getAllFines() {
        return fineRepo.findAll().stream()
                .map(fine -> modelMapper.map(fine, FineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FineDto> getFinesByBorrowId(Long borrowId) {
        return fineRepo.findByBorrowId(borrowId).stream()
                .map(fine -> modelMapper.map(fine, FineDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FineDto payFine(Long id) {
        Fine fine = fineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fine not found"));

        // Update the paid status
        fine.setPaid(true);

        // Save the updated fine
        fine = fineRepo.save(fine);

        return modelMapper.map(fine, FineDto.class);
    }

    @Override
    public void deleteFine(Long id) {
        Optional<Fine> fineOptional = fineRepo.findById(id);
        if (fineOptional.isPresent()) {
            fineRepo.delete(fineOptional.get());
        } else {
            throw new RuntimeException("Fine not found");
        }
    }
    @Override
    public LocalDateTime getDueDateByBorrowId(Long borrowId) {
        // Fetch the borrow entity to get the due date
        Borrow borrow = borrowRepo.findById(borrowId)
                .orElseThrow(() -> new EntityNotFoundException("Borrow not found for ID: " + borrowId));
        return borrow.getDueDate();
    }
    @Override
    public Double calculateFineAmount(FineDto fineDto) {
        // Assuming a fine rate of Rs 3 per day overdue
        double fineRate = 3.0;
        LocalDateTime currentDate = LocalDateTime.now();

        if (fineDto.getDueDate().isBefore(currentDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(fineDto.getDueDate(), currentDate);
            return daysOverdue * fineRate;
        }
        return 0.0;
    }
}
