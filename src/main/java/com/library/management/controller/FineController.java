package com.library.management.controller;

import com.library.management.dto.FineDto;
import com.library.management.service.FineService;
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
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    // Create Fine
    @PostMapping
    public ResponseEntity<?> createFine(@RequestParam Long borrowId) {
        try {
            FineDto createdFine = fineService.createFine(borrowId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("Fine created successfully with ID: %d and amount: Rs. %.2f.",
                            createdFine.getId(), createdFine.getAmount()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Borrow record not found with ID: %d.", borrowId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Get Fine by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFineById(@PathVariable Long id) {
        try {
            FineDto fineDto = fineService.getFineById(id);
            return ResponseEntity.ok(fineDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Fine not found with ID: %d. Please check the ID and try again.", id));
        }
    }

    // Get all fines
    @GetMapping
    public ResponseEntity<List<FineDto>> getAllFines() {
        List<FineDto> fines = fineService.getAllFines();
        return ResponseEntity.ok(fines);
    }

    // Get fines by Borrow ID
    @GetMapping("/borrow/{borrowId}")
    public ResponseEntity<List<FineDto>> getFinesByBorrowId(@PathVariable Long borrowId) {
        List<FineDto> fines = fineService.getFinesByBorrowId(borrowId);
        return ResponseEntity.ok(fines);
    }

    // Pay Fine
    @PostMapping("/pay/{id}")
    public ResponseEntity<?> payFine(@PathVariable Long id) {
        try {
            FineDto fineDto = fineService.payFine(id);
            return ResponseEntity.ok(
                    String.format("Fine with ID: %d paid successfully.", fineDto.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Fine not found with ID: %d. Unable to pay non-existent fine.", id));
        }
    }

    // Delete Fine
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFine(@PathVariable Long id) {
        try {
            fineService.deleteFine(id);
            return ResponseEntity.ok(String.format("Fine with ID: %d deleted successfully.", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Fine not found with ID: %d. Unable to delete non-existent fine.", id));
        }
    }
}
