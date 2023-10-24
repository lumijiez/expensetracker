package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.ExpenseCreationDTO;
import com.faf223.expensetrackerfaf.dto.ExpenseDTO;
import com.faf223.expensetrackerfaf.dto.mappers.ExpenseMapper;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/personal-expenses")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            List<ExpenseDTO> expenses = expenseService.getExpensesByEmail(email).stream().map(expenseMapper::toDto).collect(Collectors.toList());

            if (!expenses.isEmpty()) {
                return ResponseEntity.ok(expenses);
            }
        }

        return ResponseEntity.notFound().build();
    }
}

