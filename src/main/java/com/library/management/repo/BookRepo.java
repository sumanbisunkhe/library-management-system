package com.library.management.repo;

import com.library.management.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Book findByIsbn(String isbn);
}
