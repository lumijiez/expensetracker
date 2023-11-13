package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser(User user);
    List<Income> findByDate(LocalDate date);

    List<Income> findByDateBetween(LocalDate start, LocalDate end);
}
