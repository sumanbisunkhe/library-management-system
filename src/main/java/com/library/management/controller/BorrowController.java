package com.library.management.controller;

import com.library.management.dto.BorrowDto;
import com.library.management.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // Borrow a book
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@Valid @RequestBody BorrowDto borrowDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation errors and return them
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        BorrowDto borrowedBook = borrowService.borrowBook(borrowDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                String.format("Book borrowed successfully. Borrow ID: %d.", borrowedBook.getId()));
    }

    // Return a book
    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            BorrowDto returnedBook = borrowService.returnBook(id);
            return ResponseEntity.ok(
                    String.format("Book returned successfully. Borrow ID: %d.", returnedBook.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Borrow record not found with ID: %d. Unable to return non-existent book.", id));
        }
    }

    // Get borrow by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBorrowById(@PathVariable Long id) {
        try {
            BorrowDto borrowDto = borrowService.getBorrowById(id);
            return ResponseEntity.ok(borrowDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Borrow record not found with ID: %d.", id));
        }
    }

    // Get all borrows
    @GetMapping("/all")
    public ResponseEntity<List<BorrowDto>> getAllBorrows() {
        List<BorrowDto> borrows = borrowService.getAllBorrows();
        return ResponseEntity.ok(borrows);
    }

    // Get borrows by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowDto>> getBorrowsByUserId(@PathVariable Long userId) {
        List<BorrowDto> borrows = borrowService.getBorrowsByUserId(userId);
        return ResponseEntity.ok(borrows);
    }

    // Get borrows by book ID
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BorrowDto>> getBorrowsByBookId(@PathVariable Long bookId) {
        List<BorrowDto> borrows = borrowService.getBorrowsByBookId(bookId);
        return ResponseEntity.ok(borrows);
    }
}
