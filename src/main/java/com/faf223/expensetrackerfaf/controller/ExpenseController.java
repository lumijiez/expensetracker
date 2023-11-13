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
import com.faf223.expensetrackerfaf.util.errors.ErrorResponse;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionNotCreatedException;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionNotUpdatedException;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        else throw new TransactionsNotFoundException("Transactions not found");
    }

    @PostMapping()
    public ResponseEntity<Void> createNewExpense(@RequestBody ExpenseCreationDTO expenseDTO,
                                                       BindingResult bindingResult) {
        Expense expense = expenseMapper.toExpense(expenseDTO);

        if(bindingResult.hasErrors())
            throw new TransactionNotCreatedException(ErrorResponse.from(bindingResult).getMessage());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email);
            expense.setUser(user);

            expenseService.createOrUpdate(expense);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        throw new TransactionNotCreatedException("Could not create new expense");
    }


    // TODO: check if the expense belongs to the user
    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateExpense(@PathVariable long id, @RequestBody ExpenseCreationDTO expenseDTO,
                                                    BindingResult bindingResult) {
        Expense expense = expenseService.getTransactionById(id);

        if(expense == null) throw new TransactionsNotFoundException("The expense has not been found");

        ExpenseCategory category = expenseCategoryService.getCategoryById(expenseDTO.getExpenseCategory());
        expense.setCategory(category);
        expense.setAmount(expenseDTO.getAmount());

        if (!bindingResult.hasErrors()) {
            expenseService.createOrUpdate(expense);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new TransactionNotUpdatedException(ErrorResponse.from(bindingResult).getMessage());
        }
    }

    @GetMapping("/personal-expenses")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUser(@RequestParam Optional<LocalDate> date,
                                                              @RequestParam Optional<Month> month) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            List<ExpenseDTO> expenses;

            expenses = date.map(localDate -> expenseService.getTransactionsByDate(localDate).stream().map(expenseMapper::toDto).toList())
                    .orElseGet(() -> month.map(value -> expenseService.getTransactionsByMonth(value).stream().map(expenseMapper::toDto).toList())
                            .orElseGet(() -> expenseService.getTransactionsByEmail(email).stream().map(expenseMapper::toDto).toList()));

            if (!expenses.isEmpty()) {
                return ResponseEntity.ok(expenses);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        }

        throw new TransactionsNotFoundException("The expenses have not been found");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryService.getAllCategories();
        if (!categories.isEmpty()) return ResponseEntity.ok(categories);
        else throw new TransactionsNotFoundException("The expenses have not been found");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable long id) {
        expenseService.deleteTransactionById(id);
    }
}