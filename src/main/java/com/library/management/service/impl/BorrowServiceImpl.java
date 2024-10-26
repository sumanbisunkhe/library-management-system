package com.library.management.service.impl;

import com.library.management.dto.BorrowDto;
import com.library.management.entities.Borrow;
import com.library.management.repo.BorrowRepo;
import com.library.management.service.BorrowService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepo borrowRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public BorrowServiceImpl(BorrowRepo borrowRepo, ModelMapper modelMapper) {
        this.borrowRepo = borrowRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public BorrowDto borrowBook(BorrowDto borrowDto) {
        borrowDto.setReturnedAt(null);
        borrowDto.setIsReturned(false);
        LocalDateTime now = LocalDateTime.now();
        borrowDto.setBorrowedAt(now);
        borrowDto.setDueDate(now.plusDays(21));

        Borrow borrow = modelMapper.map(borrowDto, Borrow.class);
        Borrow savedBorrow = borrowRepo.save(borrow);
        return modelMapper.map(savedBorrow, BorrowDto.class);
    }


    @Override
    public BorrowDto returnBook(Long id) {
        Borrow existingBorrow = borrowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + id));

        existingBorrow.setIsReturned(true);
        existingBorrow.setReturnedAt(LocalDateTime.now());

        Borrow updatedBorrow = borrowRepo.save(existingBorrow);
        return modelMapper.map(updatedBorrow, BorrowDto.class);
    }

    @Override
    public BorrowDto getBorrowById(Long id) {
        Borrow borrow = borrowRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found with id: " + id));
        return modelMapper.map(borrow, BorrowDto.class);
    }

    @Override
    public List<BorrowDto> getAllBorrows() {
        List<Borrow> borrows = borrowRepo.findAll();
        return borrows.stream()
                .map(borrow -> modelMapper.map(borrow, BorrowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowDto> getBorrowsByUserId(Long userId) {
        List<Borrow> borrows = borrowRepo.findByUserId(userId);
        return borrows.stream()
                .map(borrow -> modelMapper.map(borrow, BorrowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowDto> getBorrowsByBookId(Long bookId) {
        List<Borrow> borrows = borrowRepo.findByBookId(bookId);
        return borrows.stream()
                .map(borrow -> modelMapper.map(borrow, BorrowDto.class))
                .collect(Collectors.toList());
    }
}
