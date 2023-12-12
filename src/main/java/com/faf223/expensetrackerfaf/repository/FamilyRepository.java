package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<IncomeCategory, Long> {
}
