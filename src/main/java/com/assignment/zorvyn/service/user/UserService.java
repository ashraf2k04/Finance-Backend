package com.assignment.zorvyn.service.user;

import com.assignment.zorvyn.dto.auth.LoginRequest;
import com.assignment.zorvyn.dto.auth.RegisterRequest;
import com.assignment.zorvyn.dto.user.UserResponse;
import com.assignment.zorvyn.entity.Role;
import com.assignment.zorvyn.entity.User;

import java.util.List;

public interface UserService {

    User getByEmail(String email);

    UserResponse register(RegisterRequest request);

    User login(LoginRequest request);

    List<UserResponse> getAll();

    UserResponse createUser(RegisterRequest request);

    void updateRole(Long id, Role role);

    void updateStatus(Long id, boolean active);
}