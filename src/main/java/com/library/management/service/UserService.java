package com.library.management.service;

import com.library.management.dto.UserDto;
import com.library.management.entities.User;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    UserDto activateUser(Long userId);

    UserDto deactivateUser(Long userId);

    UserDto findByUsername(String username);

    UserDto findByEmail(String email);
}
