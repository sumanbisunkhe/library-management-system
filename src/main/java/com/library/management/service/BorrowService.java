package com.library.management.service;

import com.library.management.dto.BorrowDto;

import java.util.List;

public interface BorrowService {

    BorrowDto borrowBook(BorrowDto borrowDto);

    BorrowDto returnBook(Long id);

    BorrowDto getBorrowById(Long id);

    List<BorrowDto> getAllBorrows();

    List<BorrowDto> getBorrowsByUserId(Long userId);

    List<BorrowDto> getBorrowsByBookId(Long bookId);
}
