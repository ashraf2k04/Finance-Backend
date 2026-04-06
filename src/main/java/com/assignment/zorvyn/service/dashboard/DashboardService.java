package com.assignment.zorvyn.service.dashboard;

import com.assignment.zorvyn.dto.DashboardSummaryResponse;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    DashboardSummaryResponse getSummary();

    List<Map<String, Object>> getTrends();
    List<Map<String, Object>> getCategoryBreakdown();
}