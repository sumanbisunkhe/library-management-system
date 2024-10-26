package com.library.management.utils;


import com.library.management.entities.Role;
import com.library.management.enums.RoleName;
import com.library.management.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) {
        // Create and save the default roles if they do not already exist
        createRoleIfNotExists(RoleName.ADMIN);
        createRoleIfNotExists(RoleName.LIBRARIAN);
        createRoleIfNotExists(RoleName.MEMBER);
    }

    private void createRoleIfNotExists(RoleName roleName) {
        if (!roleRepo.existsByName(roleName.toString())) { // Adjust this line based on your repo method
            Role role = new Role();
            role.setName(roleName.toString());
            roleRepo.save(role);
        }
    }
}