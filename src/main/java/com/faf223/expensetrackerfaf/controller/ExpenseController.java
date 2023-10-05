package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.ExpenseCreationDTO;
import com.faf223.expensetrackerfaf.dto.ExpenseDTO;
import com.faf223.expensetrackerfaf.dto.mappers.ExpenseMapper;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getExpenses().stream().map(expenseMapper::toDto).collect(Collectors.toList());
        if (!expenses.isEmpty()) return ResponseEntity.ok(expenses);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<ExpenseDTO> createNewExpense(@RequestBody ExpenseCreationDTO expenseDTO,
                         BindingResult bindingResult) {
        Expense expense = expenseMapper.toExpense(expenseDTO);
        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdateExpense(expense);
            return ResponseEntity.ok(expenseMapper.toDto(expense));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseCreationDTO expenseDTO,
                                                 BindingResult bindingResult) {
        Expense expense = expenseMapper.toExpense(expenseDTO);
        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdateExpense(expense);
            return ResponseEntity.ok(expenseMapper.toDto(expense));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUser(@PathVariable String userUuid) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(userUuid).stream().map(expenseMapper::toDto).collect(Collectors.toList());
        if (!expenses.isEmpty()) return ResponseEntity.ok(expenses);
        else return ResponseEntity.notFound().build();
    }
}

