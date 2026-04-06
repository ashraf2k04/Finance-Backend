package com.assignment.zorvyn.service.record;

import com.assignment.zorvyn.dto.record.RecordRequest;
import com.assignment.zorvyn.entity.FinancialRecord;
import com.assignment.zorvyn.entity.RecordType;

import java.util.List;

public interface RecordService {

    FinancialRecord create(RecordRequest request);

    List<FinancialRecord> getAll(RecordType type, String category);

    FinancialRecord getById(Long id);

    FinancialRecord update(Long id, RecordRequest request);

    void delete(Long id);
}