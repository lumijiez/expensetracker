package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public void createOrUpdateExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUserId(String userUuid) {

        Optional<User> user = userRepository.getUserByUserUuid(userUuid);
        if (user.isPresent()) {
            return expenseRepository.findByUser(user.get());
        }

        return new ArrayList<>();
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(long id) {
        return expenseRepository.findById(id).orElse(null);
    }
}