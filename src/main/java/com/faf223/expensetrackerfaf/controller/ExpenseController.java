package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable String userUuid) {
        List<Expense> expenses = expenseService.getExpensesByUserId(userUuid);
        if (!expenses.isEmpty()) {
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

