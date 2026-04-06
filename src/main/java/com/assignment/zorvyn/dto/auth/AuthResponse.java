package com.assignment.zorvyn.dto.auth;

import com.assignment.zorvyn.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    @NotBlank(message = "Token must not be empty")
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @NotNull(message = "User must not be null")
    private UserResponse user;
}