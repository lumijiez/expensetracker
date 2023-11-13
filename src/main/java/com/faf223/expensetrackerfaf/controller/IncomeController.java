package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.IncomeCreationDTO;
import com.faf223.expensetrackerfaf.dto.IncomeDTO;
import com.faf223.expensetrackerfaf.dto.mappers.IncomeMapper;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.IncomeCategoryService;
import com.faf223.expensetrackerfaf.service.IncomeService;
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
@RequestMapping("/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;
    private final UserService userService;
    private final IncomeMapper incomeMapper;
    private final IncomeCategoryService incomeCategoryService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        List<IncomeDTO> incomes = incomeService.getTransactions().stream().map(incomeMapper::toDto).collect(Collectors.toList());
        if (!incomes.isEmpty()) return ResponseEntity.ok(incomes);
        else throw new TransactionsNotFoundException("Transactions not found");
    }

    @PostMapping()
    public ResponseEntity<Void> createNewIncome(@RequestBody IncomeCreationDTO incomeDTO,
                                                     BindingResult bindingResult) {
        Income income = incomeMapper.toIncome(incomeDTO);

        if(bindingResult.hasErrors())
            throw new TransactionNotCreatedException(ErrorResponse.from(bindingResult).getMessage());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            User user = userService.getUserByEmail(email);
            income.setUser(user);

            incomeService.createOrUpdate(income);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        throw new TransactionNotCreatedException("Could not create new expense");
    }

    // TODO: check if the income belongs to the user, extract logic into service
    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateIncome(@PathVariable long id, @RequestBody IncomeCreationDTO incomeDTO,
                                                    BindingResult bindingResult) {
        Income income = incomeService.getTransactionById(id);

        if(income == null) throw new TransactionsNotFoundException("The income has not been found");

        IncomeCategory category = incomeCategoryService.getCategoryById(incomeDTO.getIncomeCategory());
        income.setCategory(category);
        income.setAmount(incomeDTO.getAmount());

        if (!bindingResult.hasErrors()) {
            incomeService.createOrUpdate(income);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new TransactionNotUpdatedException(ErrorResponse.from(bindingResult).getMessage());
        }
    }

    @GetMapping("/personal-incomes")
    public ResponseEntity<List<IncomeDTO>> getExpensesByUser(@RequestParam Optional<LocalDate> date,
                                                              @RequestParam Optional<Month> month) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            List<IncomeDTO> incomes;

            incomes = date.map(localDate -> incomeService.getTransactionsByDate(localDate).stream().map(incomeMapper::toDto).toList())
                    .orElseGet(() -> month.map(value -> incomeService.getTransactionsByMonth(value).stream().map(incomeMapper::toDto).toList())
                            .orElseGet(() -> incomeService.getTransactionsByEmail(email).stream().map(incomeMapper::toDto).toList()));

            if (!incomes.isEmpty()) {
                return ResponseEntity.ok(incomes);
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        }

        throw new TransactionsNotFoundException("The expenses have not been found");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<IncomeCategory>> getAllCategories() {
        List<IncomeCategory> categories = incomeCategoryService.getAllCategories();
        if (!categories.isEmpty()) return ResponseEntity.ok(categories);
        else throw new TransactionsNotFoundException("The expenses have not been found");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIncome(@PathVariable long id) {
        incomeService.deleteTransactionById(id);
    }
}