package com.assignment.zorvyn.controller;

import com.assignment.zorvyn.dto.auth.RegisterRequest;
import com.assignment.zorvyn.util.ApiResponse;
import com.assignment.zorvyn.dto.user.UpdateRoleRequest;
import com.assignment.zorvyn.dto.user.UpdateStatusRequest;
import com.assignment.zorvyn.dto.user.UserResponse;
import com.assignment.zorvyn.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "User management operations (Admin only)")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users fetched successfully")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.success("Users fetched successfully", userService.getAll());
    }

    @Operation(summary = "Create a new user (Admin)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Email already exists")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody RegisterRequest request) {
        return ApiResponse.success("User created successfully",
                userService.createUser(request));
    }

    @Operation(summary = "Update user role")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role")
    public ApiResponse<Void> updateRole(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody UpdateRoleRequest request) {

        userService.updateRole(id, request.getRole());
        return ApiResponse.success("User role updated successfully", null);
    }

    @Operation(summary = "Update user status (active/inactive)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {

        userService.updateStatus(id, request.isActive());
        return ApiResponse.success("User status updated successfully", null);
    }
}