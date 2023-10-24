package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.repository.IncomeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeCategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategory getExpenseCategory(long category) {
        return incomeCategoryRepository.getReferenceById(category);
    }
}