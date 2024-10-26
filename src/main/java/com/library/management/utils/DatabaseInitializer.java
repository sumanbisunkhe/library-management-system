package com.library.management.utils;

import com.library.management.entities.Role;
import com.library.management.entities.User;
import com.library.management.enums.RoleName;
import com.library.management.repo.RoleRepo; // Make sure to import RoleRepo
import com.library.management.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class DatabaseInitializer {

    private final UserRepo userRepository;
    private final RoleRepo roleRepo; // Add RoleRepo here

    @Autowired
    public DatabaseInitializer(UserRepo userRepository, RoleRepo roleRepo) {
        this.userRepository = userRepository;
        this.roleRepo = roleRepo; // Initialize RoleRepo
    }
    @PostConstruct
    public void init() {
        // Create roles first
        Role adminRole = createRoleIfNotExists(RoleName.ADMIN);
        Role librarianRole = createRoleIfNotExists(RoleName.LIBRARIAN);
        Role memberRole = createRoleIfNotExists(RoleName.MEMBER);

        if (!userRepository.existsByEmail("sumanbisunkhe304@gmail.com")) {
            User user = new User();
            user.setUsername("Suman");
            user.setPassword("$2a$12$CgeWqCls7y1lOl4U7umNEeBNoSUExhG2dgfJseWY27O.jlHnCKt8e");
            user.setEmail("sumanbisunkhe304@gmail.com");
            user.setFullName("Suman Bisunkhe");
            user.setDateOfBirth(LocalDate.of(2004, 02, 25));
            user.setPhoneNumber("9840948274");
            user.setAddress("Kathmandu, Nepal");

            // Setting roles
            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(librarianRole);
            roles.add(memberRole);
            user.setRoles(roles);

            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setIsActive(true);

            userRepository.save(user);
        }
    }

    private Role createRoleIfNotExists(RoleName roleName) {
        if (!roleRepo.existsByName(roleName.toString())) {
            Role role = new Role();
            role.setName(roleName.toString());
            roleRepo.save(role);
            return role; // Return the newly created role
        } else {
            return roleRepo.findByName(roleName.toString()).orElse(null); // Return null if not found (ideally shouldn't happen)
        }
    }


}
