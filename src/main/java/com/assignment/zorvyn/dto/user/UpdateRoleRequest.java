package com.assignment.zorvyn.dto.user;

import com.assignment.zorvyn.entity.Role;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UpdateRoleRequest {

    @Schema(example = "ADMIN")
    private Role role;
}
