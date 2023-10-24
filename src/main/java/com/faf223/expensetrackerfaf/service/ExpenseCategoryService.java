package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import com.faf223.expensetrackerfaf.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService implements ICategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public List<ExpenseCategory> getAllCategories() {
        return expenseCategoryRepository.findAll();
    }

    @Override
    public ExpenseCategory getCategoryById(long id) {
        return expenseCategoryRepository.getReferenceById(id);
    }

}