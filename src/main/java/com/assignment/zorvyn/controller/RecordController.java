package com.assignment.zorvyn.controller;

import com.assignment.zorvyn.entity.RecordType;
import com.assignment.zorvyn.util.ApiResponse;
import com.assignment.zorvyn.dto.record.RecordRequest;
import com.assignment.zorvyn.entity.FinancialRecord;
import com.assignment.zorvyn.service.record.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@Tag(name = "Records API", description = "Operations related to financial records")
public class RecordController {

    private final RecordService recordService;

    @Operation(summary = "Create a new financial record")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Record created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ApiResponse<FinancialRecord> create(
            @RequestBody RecordRequest request) {

        FinancialRecord record = recordService.create(request);
        return ApiResponse.success("Record created successfully", record);
    }

    @Operation(summary = "Get all records with optional filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Records fetched successfully")
    })
    @GetMapping
    public ApiResponse<List<FinancialRecord>> getAll(

            @Parameter(description = "Filter by type (INCOME / EXPENSE)")
            @RequestParam(required = false) String type,

            @Parameter(description = "Filter by category (e.g., Food, Salary)")
            @RequestParam(required = false) String category
    ) {
        return ApiResponse.success("Records fetched successfully",
                recordService.getAll(RecordType.valueOf(type), category));
    }

    @Operation(summary = "Get a record by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Record fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Record not found")
    })
    @GetMapping("/{id}")
    public ApiResponse<FinancialRecord> getById(
            @Parameter(description = "Record ID") @PathVariable Long id) {

        return ApiResponse.success("Record fetched successfully",
                recordService.getById(id));
    }

    @Operation(summary = "Update a record by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Record not found")
    })
    @PutMapping("/{id}")
    public ApiResponse<FinancialRecord> update(

            @Parameter(description = "Record ID") @PathVariable Long id,

            @RequestBody RecordRequest request) {

        return ApiResponse.success("Record updated successfully",
                recordService.update(id, request));
    }

    @Operation(summary = "Delete a record by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Record deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Record not found")
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @Parameter(description = "Record ID") @PathVariable Long id) {

        recordService.delete(id);
        return ApiResponse.success("Record deleted successfully", null);
    }
}