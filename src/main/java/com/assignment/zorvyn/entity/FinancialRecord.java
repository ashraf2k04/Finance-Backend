package com.assignment.zorvyn.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RecordType type;

    private String category;

    private LocalDate date;

    private String notes;

    @Builder.Default
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user = null;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}