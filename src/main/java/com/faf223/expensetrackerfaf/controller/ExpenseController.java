package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.ExpenseCreationDTO;
import com.faf223.expensetrackerfaf.dto.ExpenseDTO;
import com.faf223.expensetrackerfaf.dto.mappers.ExpenseMapper;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.ExpenseCategoryService;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import com.faf223.expensetrackerfaf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryService expenseCategoryService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getTransactions().stream().map(expenseMapper::toDto).collect(Collectors.toList());
        if (!expenses.isEmpty()) return ResponseEntity.ok(expenses);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Void> createNewExpense(@RequestBody ExpenseCreationDTO expenseDTO,
                                                       BindingResult bindingResult) {
        Expense expense = expenseMapper.toExpense(expenseDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email);
            expense.setUser(user);

            expenseService.createOrUpdate(expense);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.notFound().build();
    }


    // TODO: has to be checked on auto extracting Uuid
    @PatchMapping()
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestBody ExpenseCreationDTO expenseDTO,
                                                    BindingResult bindingResult) {
        Expense expense = expenseMapper.toExpense(expenseDTO);
        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdate(expense);
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
            List<ExpenseDTO> expenses = expenseService.getTransactionsByEmail(email).stream().map(expenseMapper::toDto).collect(Collectors.toList());

            if (!expenses.isEmpty()) {
                return ResponseEntity.ok(expenses);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryService.getAllCategories();
        if (!categories.isEmpty()) return ResponseEntity.ok(categories);
        else return ResponseEntity.notFound().build();
    }
}