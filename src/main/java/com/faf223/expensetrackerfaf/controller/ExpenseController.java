package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getExpenses();
        if (!expenses.isEmpty()) return ResponseEntity.ok(expenses);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Expense> createNewExpense(@RequestBody Expense expense,
                         BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdateExpense(expense);
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense,
                                                 BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdateExpense(expense);
            return ResponseEntity.ok(expense);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable String userUuid) {
        List<Expense> expenses = expenseService.getExpensesByUserId(userUuid);
        if (!expenses.isEmpty()) return ResponseEntity.ok(expenses);
        else return ResponseEntity.notFound().build();
    }
}

