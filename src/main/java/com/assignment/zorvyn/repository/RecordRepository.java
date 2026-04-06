package com.assignment.zorvyn.repository;

import com.assignment.zorvyn.entity.FinancialRecord;
import com.assignment.zorvyn.entity.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<FinancialRecord, Long> {

    // 🔍 Filtering
    List<FinancialRecord> findByType(RecordType type);

    List<FinancialRecord> findByCategory(String category);

    List<FinancialRecord> findByTypeAndCategory(RecordType type, String category);

    List<FinancialRecord> findByDateBetween(LocalDate start, LocalDate end);

    // 📊 Dashboard Aggregations

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.type = :type")
    Double sumByType(@Param("type") RecordType type);

    @Query("""
        SELECT r.category, SUM(r.amount)
        FROM FinancialRecord r
        GROUP BY r.category
    """)
    List<Object[]> getCategoryWiseTotals();

    @Query(value = """
    SELECT EXTRACT(MONTH FROM r.date), SUM(r.amount)
    FROM records r
    GROUP BY EXTRACT(MONTH FROM r.date)
    ORDER BY EXTRACT(MONTH FROM r.date)
""", nativeQuery = true)
    List<Object[]> getMonthlyTrends();
}