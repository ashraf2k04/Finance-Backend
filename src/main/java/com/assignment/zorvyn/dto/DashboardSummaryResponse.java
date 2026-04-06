package com.assignment.zorvyn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
public class DashboardSummaryResponse {

    @Schema(example = "50000")
    private double totalIncome;

    @Schema(example = "20000")
    private double totalExpense;

    @Schema(example = "30000")
    private double netBalance;
}