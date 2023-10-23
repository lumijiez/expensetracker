package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "income_categories")
public class IncomeCategory implements IMoneyTransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;
}

