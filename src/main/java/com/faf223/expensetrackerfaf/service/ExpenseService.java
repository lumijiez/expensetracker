package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpensesByUserId(String userUuid) {
        return expenseRepository.findByUserUserUuid(userUuid);
    }
}