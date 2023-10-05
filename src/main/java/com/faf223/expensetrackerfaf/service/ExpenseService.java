package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public void createOrUpdateExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUserId(String userUuid) {
        return expenseRepository.findByUserUuid(userUuid);
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElse(null);
    }
}