package com.library.management.repo;

import com.library.management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    // Method to find a user by username
    Optional<User> findByUsername(String username);

    // Method to find a user by email
    Optional<User> findByEmail(String email);

    // Method to find a user by phone number
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);

    Optional<User> findByUsernameOrEmail(String identifier, String identifier1);

    Optional<User>  findById(long id);


}