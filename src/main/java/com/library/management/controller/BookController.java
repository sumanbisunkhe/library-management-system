package com.library.management.controller;

import com.library.management.dto.BookDto;
import com.library.management.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController extends ResponseEntityExceptionHandler {

    @Autowired
    private BookService bookService;

    // Add Book
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation errors and return them
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            BookDto savedBook = bookService.addBook(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    String.format("Book '%s' added successfully with ISBN: %s.", savedBook.getTitle(), savedBook.getIsbn()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the book. Please try again.");
        }
    }

    // Update Book
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @Valid @RequestBody BookDto bookDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            BookDto updatedBook = bookService.updateBook(id, bookDto);
            return ResponseEntity.ok(
                    String.format("Book with ID: %d updated successfully. New title: '%s'.", updatedBook.getId(), updatedBook.getTitle()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Book not found with ID: %d. Unable to update non-existent book.", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the book.");
        }
    }

    // Get Book by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            BookDto bookDto = bookService.getBookById(id);
            return ResponseEntity.ok(bookDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Book not found with ID: %d. Please check the ID and try again.", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the book details.");
        }
    }

    // Get All Books
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        try {
            List<BookDto> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get Book by ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
        try {
            BookDto bookDto = bookService.getBookByIsbn(isbn);
            return ResponseEntity.ok(bookDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Book not found with ISBN: '%s'. Please check the ISBN and try again.", isbn));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the book details.");
        }
    }

    // Delete Book
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok(String.format("Book with ID: %d deleted successfully.", id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("Book not found with ID: %d. Unable to delete non-existent book.", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the book.");
        }
    }

}
