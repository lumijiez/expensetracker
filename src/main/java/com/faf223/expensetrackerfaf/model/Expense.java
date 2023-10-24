package com.faf223.expensetrackerfaf.model;
import com.faf223.expensetrackerfaf.util.IMoneyTransaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "expenses")
public class Expense implements IMoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long id;

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

    public Expense(LocalDate date, BigDecimal amount) {
    }

    public Expense(ExpenseCategory expenseCategory, LocalDate date, BigDecimal amount) {
        this.category = expenseCategory;
        this.date = date;
        this.amount = amount;
    }
}