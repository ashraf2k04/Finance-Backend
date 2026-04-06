package com.assignment.zorvyn.dto.user;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class UpdateStatusRequest {

    @Schema(example = "true")
    private boolean active;
}