package com.faf223.expensetrackerfaf.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @ManyToOne()
    @JoinColumn(name = "user_uuid")
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    private LocalDate date;
    private BigDecimal amount;
}

