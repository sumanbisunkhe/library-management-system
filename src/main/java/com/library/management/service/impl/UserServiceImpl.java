package com.library.management.service.impl;

import com.library.management.dto.UserDto;
import com.library.management.entities.Role;
import com.library.management.entities.User;
import com.library.management.enums.RoleName;
import com.library.management.repo.RoleRepo;
import com.library.management.repo.UserRepo;
import com.library.management.service.EmailService;
import com.library.management.service.UserService;
import com.library.management.utils.CustomEmailMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepository;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto registerUser(UserDto userDto) {
        // Encrypt the password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Map userDto to User entity
        User user = modelMapper.map(userDto, User.class);

        // Set default role to MEMBER
        Set<Role> roles = new HashSet<>();
        Role memberRole = roleRepo.findByName(RoleName.MEMBER.toString())
                .orElseThrow(() -> new RuntimeException("Role not found: MEMBER"));
        roles.add(memberRole);
        user.setRoles(roles); // Set roles to MEMBER by default

        // Set createdAt and isActive values
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);

        // Save the user to the repository
        user = userRepository.save(user);

        logger.info("Registered new user: {}", user.getUsername());

        // Prepare the email message
        CustomEmailMessage emailMessage = new CustomEmailMessage();
        emailMessage.setFrom("readymade090@gmail.com");
        emailMessage.setTo(user.getEmail());
        emailMessage.setSentDate(new Date());
        emailMessage.setSubject("Welcome to the Library Management System");
        emailMessage.setText(String.format(
                "Hello %s,\n\n\nThank you for registering in our Library Management System! We're glad to have you on board.\n\n\nBest regards,\nLibrary Management Team",
                user.getFullName()
        ));

        // Send registration success email
        emailService.sendNotificationEmail(emailMessage);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Update the password if it's provided
        if (userDto.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encrypt if password is being updated
        }

        // Set role to MEMBER regardless of what is provided
        Set<Role> roles = new HashSet<>();
        Role memberRole = roleRepo.findByName(RoleName.MEMBER.toString())
                .orElseThrow(() -> new RuntimeException("Role not found: MEMBER"));
        roles.add(memberRole);
        existingUser.setRoles(roles); // Set the roles to MEMBER

        // Retain original createdAt and isActive values
        LocalDateTime createdAt = existingUser.getCreatedAt();
        Boolean isActive = existingUser.getIsActive();

        // Configure modelMapper to skip the `id`, `createdAt`, and `isActive` fields mapping
        modelMapper.typeMap(UserDto.class, User.class).addMappings(mapper -> {
            mapper.skip(User::setId);
            mapper.skip(User::setCreatedAt);
            mapper.skip(User::setIsActive);
        });

        // Map remaining fields from userDto to existingUser, excluding `id`, `createdAt`, and `isActive`
        modelMapper.map(userDto, existingUser);

        // Reapply the preserved values
        existingUser.setCreatedAt(createdAt);
        existingUser.setIsActive(isActive);

        // Save the updated user to the repository
        existingUser = userRepository.save(existingUser);
        logger.info("Updated user: {}", existingUser.getUsername());
        return modelMapper.map(existingUser, UserDto.class);
    }




    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Manually map roles from User to UserDto
        Set<RoleName> roleNames = user.getRoles().stream()
                .map(role -> RoleName.valueOf(role.getName()))
                .collect(Collectors.toSet());
        userDto.setRoles(roleNames);

        return userDto;
    }


    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);

                    // Manually map roles from User to UserDto
                    Set<RoleName> roleNames = user.getRoles().stream()
                            .map(role -> RoleName.valueOf(role.getName()))
                            .collect(Collectors.toSet());
                    userDto.setRoles(roleNames);

                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        userRepository.delete(existingUser);
        logger.info("Deleted user: {}", existingUser.getUsername());
    }

    @Override
    public UserDto activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setIsActive(true);
        user = userRepository.save(user);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Manually map roles from User to UserDto
        Set<RoleName> roleNames = user.getRoles().stream()
                .map(role -> RoleName.valueOf(role.getName()))
                .collect(Collectors.toSet());
        userDto.setRoles(roleNames);

        return userDto;
    }

    @Override
    public UserDto deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setIsActive(false);
        user = userRepository.save(user);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Manually map roles from User to UserDto
        Set<RoleName> roleNames = user.getRoles().stream()
                .map(role -> RoleName.valueOf(role.getName()))
                .collect(Collectors.toSet());
        userDto.setRoles(roleNames);

        return userDto;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Manually map roles from User to UserDto
        Set<RoleName> roleNames = user.getRoles().stream()
                .map(role -> RoleName.valueOf(role.getName()))
                .collect(Collectors.toSet());
        userDto.setRoles(roleNames);

        return userDto;
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Manually map roles from User to UserDto
        Set<RoleName> roleNames = user.getRoles().stream()
                .map(role -> RoleName.valueOf(role.getName()))
                .collect(Collectors.toSet());
        userDto.setRoles(roleNames);

        return userDto;
    }
}
