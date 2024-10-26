package com.library.management.service.impl;

import com.library.management.dto.BookDto;
import com.library.management.entities.Book;

import com.library.management.repo.BookRepo;
import com.library.management.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, ModelMapper modelMapper) {
        this.bookRepo = bookRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        if (bookRepo.existsByIsbn(bookDto.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + bookDto.getIsbn() + " already exists.");
        }

        Book book = modelMapper.map(bookDto, Book.class);
        Book savedBook = bookRepo.save(book);
        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book existingBook = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setCategory(bookDto.getCategory());
        existingBook.setAvailable(bookDto.getAvailable());

        Book updatedBook = bookRepo.save(existingBook);
        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepo.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepo.deleteById(id);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        Book book = bookRepo.findByIsbn(isbn);
        if (book == null) {
            throw new RuntimeException("Book not found with ISBN: " + isbn);
        }
        return modelMapper.map(book, BookDto.class);
    }
}
