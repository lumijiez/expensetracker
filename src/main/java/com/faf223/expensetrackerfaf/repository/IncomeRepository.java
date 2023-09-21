package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
