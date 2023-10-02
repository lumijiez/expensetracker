package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserUserUuid(String userUuid);
}
