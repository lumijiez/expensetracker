package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByDate(LocalDate date);

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
}
