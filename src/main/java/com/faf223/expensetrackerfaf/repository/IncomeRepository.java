package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
    List<Income> findByDate(LocalDate date);

    List<Income> findByDateBetween(LocalDate start, LocalDate end);

    @Procedure(procedureName = "get_incomes_by_month")
    List<Income> filterByMonth(int month);

    @Procedure(procedureName = "get_last_week_incomes")
    List<Income> findLastWeek();

    @Procedure(procedureName = "get_last_month_incomes")
    List<Income> findLastMonth();

    @Procedure(procedureName = "get_incomes_by_year_interval")
    List<Income> filterByYearInterval(int start, int end);
}
