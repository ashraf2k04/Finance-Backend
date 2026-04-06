package com.assignment.zorvyn.service.user;

import com.assignment.zorvyn.dto.auth.LoginRequest;
import com.assignment.zorvyn.dto.auth.RegisterRequest;
import com.assignment.zorvyn.dto.user.UserResponse;
import com.assignment.zorvyn.entity.Role;
import com.assignment.zorvyn.entity.Status;
import com.assignment.zorvyn.entity.User;
import com.assignment.zorvyn.exception.BadRequestException;
import com.assignment.zorvyn.exception.ResourceNotFoundException;
import com.assignment.zorvyn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isLoggedIn = auth != null &&
                auth.isAuthenticated() &&
                !"anonymousUser".equals(auth.getPrincipal());

        boolean isAdmin = isLoggedIn &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isAdminRequest = auth != null &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isLoggedIn && !isAdmin) {
            throw new BadRequestException("Only admin can create users while logged in");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // ⚠ hash later
        if (isAdminRequest && request.getRole() != null) {
            user.setRole(request.getRole());
        }

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public User login(LoginRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isLoggedIn = auth != null &&
                auth.isAuthenticated() &&
                !"anonymousUser".equals(auth.getPrincipal());

        if (isLoggedIn) {
            throw new BadRequestException("User already logged in");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new BadRequestException("User is inactive");
        }

        return user;
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse createUser(RegisterRequest request) {
        return register(request);
    }

    @Override
    public void updateRole(Long id, Role role) {
        User user = getUserOrThrow(id);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void updateStatus(Long id, boolean active) {
        User user = getUserOrThrow(id);
        user.setStatus(active ? Status.ACTIVE : Status.INACTIVE);
        userRepository.save(user);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }

}