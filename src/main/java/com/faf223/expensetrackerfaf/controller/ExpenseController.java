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
import com.faf223.expensetrackerfaf.util.exceptions.TransactionDoesNotBelongToTheUserException;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionNotCreatedException;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionNotUpdatedException;
import com.faf223.expensetrackerfaf.util.exceptions.TransactionsNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
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
    public ResponseEntity<Map<String, Long>> createNewExpense(@RequestBody @Valid ExpenseCreationDTO expenseDTO,
                                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new TransactionNotCreatedException("Could not create new expense");

        Expense expense = expenseMapper.toExpense(expenseDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email);
            expense.setUser(user);

            expenseService.createOrUpdate(expense);
            Map<String, Long> response = new HashMap<>();
            response.put("expenseId", expense.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        throw new TransactionNotCreatedException("Could not create new expense");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateExpense(@PathVariable long id, @RequestBody @Valid ExpenseCreationDTO expenseDTO,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new TransactionNotUpdatedException(ErrorResponse.from(bindingResult).getMessage());

        Expense expense = expenseService.getTransactionById(id);

        if(expense == null)
            throw new TransactionsNotFoundException("The expense has not been found");

        if(!expenseService.belongsToUser(expense))
            throw new TransactionDoesNotBelongToTheUserException("The transaction does not belong to you");

        ExpenseCategory category = expenseCategoryService.getCategoryById(expenseDTO.getExpenseCategory());
        expense.setCategory(category);
        expense.setAmount(expenseDTO.getAmount());

        expenseService.createOrUpdate(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/personal-expenses")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUser(@RequestParam Optional<LocalDate> date,
                                                              @RequestParam Optional<Integer> month,
                                                              @RequestParam Optional<Integer> startYear,
                                                              @RequestParam Optional<Integer> endYear,
                                                              @RequestParam Optional<String> lastUnit) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            List<ExpenseDTO> expenses = Collections.emptyList();

            if(date.isPresent())
                expenses = expenseService.getTransactionsByDate(date.get(), email).stream().map(expenseMapper::toDto).toList();
            else if(month.isPresent())
                expenses = expenseService.getTransactionsByMonth(Month.of(month.get()), email).stream().map(expenseMapper::toDto).toList();
            else if(startYear.isPresent() && endYear.isPresent())
                expenses = expenseService.getYearIntervalTransactions(email, startYear.get(), endYear.get()).stream().map(expenseMapper::toDto).toList();
            else if(lastUnit.isPresent()) {

                if(lastUnit.get().equalsIgnoreCase("week"))
                    expenses = expenseService.getLastWeekTransactions(email).stream().map(expenseMapper::toDto).toList();
                else if(lastUnit.get().equalsIgnoreCase("month"))
                    expenses = expenseService.getLastMonthTransactions(email).stream().map(expenseMapper::toDto).toList();

            } else {
                expenses = userService.getUserByEmail(email).getExpenses().stream().map(expenseMapper::toDto).toList();
            }

            return ResponseEntity.ok(expenses);
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