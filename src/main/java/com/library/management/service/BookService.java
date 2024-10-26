package com.library.management.service;

import com.library.management.dto.BookDto;
import com.library.management.entities.Book;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    BookDto updateBook(Long id, BookDto bookDto);
    void deleteBook(Long id);
    BookDto getBookById(Long id);
    List<BookDto> getAllBooks();
    BookDto getBookByIsbn(String isbn);
}
