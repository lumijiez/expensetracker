package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import com.faf223.expensetrackerfaf.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategory getExpenseCategory(long category) {
        return expenseCategoryRepository.getReferenceById(category);
    }

}