package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
