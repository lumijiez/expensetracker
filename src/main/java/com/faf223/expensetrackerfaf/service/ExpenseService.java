package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getExpensesByUserId(String userUuid) {
        return expenseRepository.findByUserUserUuid(userUuid);
    }
}