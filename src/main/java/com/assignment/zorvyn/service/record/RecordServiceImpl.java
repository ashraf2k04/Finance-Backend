package com.assignment.zorvyn.service.record;

import com.assignment.zorvyn.dto.record.RecordRequest;
import com.assignment.zorvyn.entity.FinancialRecord;
import com.assignment.zorvyn.entity.RecordType;
import com.assignment.zorvyn.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    @Override
    public FinancialRecord create(RecordRequest request) {

        FinancialRecord record = new FinancialRecord();
        mapToEntity(record, request);

        return recordRepository.save(record);
    }

    @Override
    public List<FinancialRecord> getAll(RecordType type, String category) {

        if (type != null && category != null) {
            return recordRepository.findByTypeAndCategory(type, category);
        }

        if (type != null) {
            return recordRepository.findByType(type);
        }

        if (category != null) {
            return recordRepository.findByCategory(category);
        }

        return recordRepository.findAll();
    }

    @Override
    public FinancialRecord getById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    @Override
    public FinancialRecord update(Long id, RecordRequest request) {

        FinancialRecord record = getById(id);
        mapToEntity(record, request);

        return recordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        FinancialRecord record = getById(id);
        recordRepository.delete(record);
    }

    private void mapToEntity(FinancialRecord record, RecordRequest request) {
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setDate(request.getDate());
        record.setNotes(request.getNotes());
    }
}