package com.assignment.zorvyn.dto.record;

import com.assignment.zorvyn.entity.RecordType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecordRequest {

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Type is required")
    private RecordType type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Nullable
    private String Notes;
}