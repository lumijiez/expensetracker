package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByDate(LocalDate date);

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

    @Procedure(procedureName = "get_expenses_by_month")
    List<Expense> filterByMonth(int month);

    @Procedure(procedureName = "get_last_week_expenses")
    List<Expense> findLastWeek();

    @Procedure(procedureName = "get_last_month_expenses")
    List<Expense> findLastMonth();

    @Procedure(procedureName = "get_expenses_by_year_interval")
    List<Expense> filterByYearInterval(int start, int end);
}
