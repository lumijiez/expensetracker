package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "expense_categories")
public class ExpenseCategory implements IMoneyTransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;
}
