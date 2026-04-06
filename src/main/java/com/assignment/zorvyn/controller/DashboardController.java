package com.assignment.zorvyn.controller;

import com.assignment.zorvyn.dto.DashboardSummaryResponse;
import com.assignment.zorvyn.util.ApiResponse;
import com.assignment.zorvyn.service.dashboard.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard API", description = "Analytics and financial insights")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Get dashboard summary (income, expense, balance)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Summary fetched successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/summary")
    public ApiResponse<DashboardSummaryResponse> getSummary() {
        return ApiResponse.success(
                "Dashboard summary fetched successfully",
                dashboardService.getSummary()
        );
    }

    @Operation(summary = "Get monthly trends")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Trends fetched successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/trends")
    public ApiResponse<List<Map<String, Object>>> getTrends() {
        return ApiResponse.success(
                "Trends fetched successfully",
                dashboardService.getTrends()
        );
    }

    @Operation(summary = "Get category-wise breakdown")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category breakdown fetched successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/category")
    public ApiResponse<List<Map<String, Object>>> getCategoryBreakdown() {
        return ApiResponse.success(
                "Category breakdown fetched successfully",
                dashboardService.getCategoryBreakdown()
        );
    }
}