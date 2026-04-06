package com.assignment.zorvyn.service.dashboard;

import com.assignment.zorvyn.dto.DashboardSummaryResponse;
import com.assignment.zorvyn.entity.RecordType;
import com.assignment.zorvyn.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final RecordRepository recordRepository;

    @Override
    public DashboardSummaryResponse getSummary() {

        double income = recordRepository.sumByType(RecordType.INCOME);
        double expense = recordRepository.sumByType(RecordType.EXPENSE);

        return new DashboardSummaryResponse(
                income,
                expense,
                income - expense
        );
    }

    @Override
    public List<Map<String, Object>> getTrends() {

        return recordRepository.getMonthlyTrends()
                .stream()
                .map(obj -> Map.of(
                        "month", obj[0],
                        "total", obj[1]
                ))
                .toList();
    }

    @Override
    public List<Map<String, Object>> getCategoryBreakdown() {

        return recordRepository.getCategoryWiseTotals()
                .stream()
                .map(obj -> Map.of(
                        "category", obj[0],
                        "total", obj[1]
                ))
                .toList();
    }
}