package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.repository.IncomeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeCategoryService implements ICategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    @Override
    public List<IncomeCategory> getAllCategories() {
        return incomeCategoryRepository.findAll();
    }

    @Override
    public IncomeCategory getCategoryById(long id) {
        return incomeCategoryRepository.getReferenceById(id);
    }
}