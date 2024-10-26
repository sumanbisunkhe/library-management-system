package com.library.management.repo;

import com.library.management.entities.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepo extends JpaRepository<Fine, Long> {
    List<Fine> findByBorrowId(Long borrowId);
    List<Fine> findByPaid(Boolean paid);

    boolean existsByBorrowId(Long borrowId);
}
