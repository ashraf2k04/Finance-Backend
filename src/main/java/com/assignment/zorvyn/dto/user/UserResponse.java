package com.assignment.zorvyn.dto.user;

import com.assignment.zorvyn.entity.Role;
import com.assignment.zorvyn.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Ali")
    private String name;

    @Schema(example = "ali@email.com")
    private String email;

    @Schema(example = "VIEWER")
    private Role role;

    @Schema(example = "ACTIVE")
    private Status status;
}