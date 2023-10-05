package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserUuid(String userUuid);
}
