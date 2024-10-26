package com.library.management.controller;

import com.library.management.dto.UserDto;
import com.library.management.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation errors and return them
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        UserDto registeredUser = userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                String.format("User '%s' registered successfully with ID: %d.", registeredUser.getUsername(), registeredUser.getId()));
    }

    // Update User
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @Valid @RequestBody UserDto userDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Collect validation errors and return them
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        UserDto updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(
                String.format("User with ID: %d updated successfully. New username: '%s'.", updatedUser.getId(), updatedUser.getUsername()));
    }

    // Get User by ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            UserDto userDto = userService.getUserById(userId);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with ID: %d. Please check the ID and try again.", userId));
        }
    }

    // Get All Users
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Delete User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(String.format("User with ID: %d deleted successfully.", userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with ID: %d. Unable to delete non-existent user.", userId));
        }
    }

    // Activate User
    @PostMapping("/activate/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        try {
            UserDto activatedUser = userService.activateUser(userId);
            return ResponseEntity.ok(
                    String.format("User with ID: %d activated successfully. Current status: '%s'.", activatedUser.getId(), activatedUser.getIsActive() ? "Active" : "Inactive"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with ID: %d. Unable to activate non-existent user.", userId));
        }
    }

    // Deactivate User
    @PostMapping("/deactivate/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
        try {
            UserDto deactivatedUser = userService.deactivateUser(userId);
            return ResponseEntity.ok(
                    String.format("User with ID: %d deactivated successfully. Current status: '%s'.", deactivatedUser.getId(), deactivatedUser.getIsActive() ? "Active" : "Inactive"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with ID: %d. Unable to deactivate non-existent user.", userId));
        }
    }

    // Find User by Username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            UserDto userDto = userService.findByUsername(username);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with username: '%s'. Please check the username and try again.", username));
        }
    }

    // Find User by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            UserDto userDto = userService.findByEmail(email);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    String.format("User not found with email: '%s'. Please check the email and try again.", email));
        }
    }
}
