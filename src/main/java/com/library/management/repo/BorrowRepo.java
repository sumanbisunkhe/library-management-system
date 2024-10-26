package com.library.management.repo;

import com.library.management.entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRepo extends JpaRepository<Borrow,Long> {
    List<Borrow> findByUserId(Long userId);

    List<Borrow> findByBookId(Long bookId);
}
