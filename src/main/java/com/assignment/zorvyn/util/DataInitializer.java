package com.assignment.zorvyn.util;

import com.assignment.zorvyn.entity.Role;
import com.assignment.zorvyn.entity.Status;
import com.assignment.zorvyn.entity.User;
import com.assignment.zorvyn.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {

        if (userRepository.findByEmail("admin@zorvyn.com").isEmpty()) {

            User admin = User.builder()
                    .name("Admin")
                    .email("admin@zorvyn.com")
                    .password(passwordEncoder.encode("admin123")) // ⚠ hash later
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .build();

            userRepository.save(admin);
        }
    }
}