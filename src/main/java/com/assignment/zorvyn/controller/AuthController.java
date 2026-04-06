package com.assignment.zorvyn.controller;

import com.assignment.zorvyn.config.JwtUtil;
import com.assignment.zorvyn.dto.auth.AuthResponse;
import com.assignment.zorvyn.dto.auth.LoginRequest;
import com.assignment.zorvyn.dto.auth.RegisterRequest;
import com.assignment.zorvyn.exception.BadRequestException;
import com.assignment.zorvyn.util.ApiResponse;
import com.assignment.zorvyn.dto.user.UserResponse;
import com.assignment.zorvyn.entity.User;
import com.assignment.zorvyn.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Authentication APIs")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Register user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully")
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse user = userService.register(request);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "User registered successfully",
                        new AuthResponse(token, user)
                ));
    }

    @Operation(summary = "Login user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userService.login(request);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful",
                        new AuthResponse(token, response)
                )
        );
    }

    @Operation(summary = "Get current user")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> me(Authentication authentication) {

        User user = userService.getByEmail(authentication.getName());

        UserResponse response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );

        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", response)
        );
    }
}