package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
@Entity(name = "expense_categories")
public class ExpenseCategory implements IMoneyTransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long categoryId;

    @Getter(AccessLevel.NONE)
    private String categoryName;

    @Override
    public Long getId() {
        return categoryId;
    }

    @Override
    public String getName() {
        return categoryName;
    }

}
