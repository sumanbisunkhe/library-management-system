package com.library.management.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "borrows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrowed_at", nullable = false)
    private LocalDateTime borrowedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "is_returned", nullable = false)
    private Boolean isReturned;

    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Fine> fines = new HashSet<>();

    // Constructor to initialize dueDate based on borrowedAt
    public Borrow(User user, Book book, LocalDateTime borrowedAt) {
        this.user = user;
        this.book = book;
        this.borrowedAt = borrowedAt;
        this.dueDate = borrowedAt.plusDays(21); // Set due date to 21 days after borrowedAt
        this.isReturned = false; // Default to not returned
    }
}
