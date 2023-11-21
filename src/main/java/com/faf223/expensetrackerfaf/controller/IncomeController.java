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
    public ResponseEntity<Void> createNewIncome(@RequestBody @Valid IncomeCreationDTO incomeDTO,
                                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new TransactionNotCreatedException(ErrorResponse.from(bindingResult).getMessage());

        Income income = incomeMapper.toIncome(incomeDTO);

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

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateIncome(@PathVariable long id, @RequestBody @Valid IncomeCreationDTO incomeDTO,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new TransactionNotUpdatedException(ErrorResponse.from(bindingResult).getMessage());

        Income income = incomeService.getTransactionById(id);

        if(income == null)
            throw new TransactionsNotFoundException("The income has not been found");

        if(!incomeService.belongsToUser(income))
            throw new TransactionDoesNotBelongToTheUserException("The transaction does not belong to you");

        IncomeCategory category = incomeCategoryService.getCategoryById(incomeDTO.getIncomeCategory());
        income.setCategory(category);
        income.setAmount(incomeDTO.getAmount());

        incomeService.createOrUpdate(income);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/personal-incomes")
    @Transactional(readOnly = true)
    public ResponseEntity<List<IncomeDTO>> getIncomesByUser(@RequestParam Optional<LocalDate> date,
                                                             @RequestParam Optional<Integer> month,
                                                             @RequestParam Optional<Integer> startYear,
                                                             @RequestParam Optional<Integer> endYear,
                                                             @RequestParam Optional<String> lastUnit) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();
            List<IncomeDTO> incomes = Collections.emptyList();

            if(date.isPresent())
                incomes = incomeService.getTransactionsByDate(date.get(), email).stream().map(incomeMapper::toDto).toList();
            else if(month.isPresent())
                incomes = incomeService.getTransactionsByMonth(Month.of(month.get()), email).stream().map(incomeMapper::toDto).toList();
            else if(startYear.isPresent() && endYear.isPresent())
                incomes = incomeService.getYearIntervalTransactions(email, startYear.get(), endYear.get()).stream().map(incomeMapper::toDto).toList();
            else if(lastUnit.isPresent()) {

                if(lastUnit.get().equalsIgnoreCase("week"))
                    incomes = incomeService.getLastWeekTransactions(email).stream().map(incomeMapper::toDto).toList();
                else if(lastUnit.get().equalsIgnoreCase("month"))
                    incomes = incomeService.getLastMonthTransactions(email).stream().map(incomeMapper::toDto).toList();

            }

            return ResponseEntity.ok(incomes);
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