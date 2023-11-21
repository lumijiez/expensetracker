package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.*;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.util.TransactionFilter;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotAuthenticatedException;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService implements ITransactionService {

    private final ExpenseRepository expenseRepository;
    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final TransactionFilter transactionFilter;

    public void createOrUpdate(IMoneyTransaction expense) {
        expenseRepository.save((Expense) expense);
    }

    public List<Expense> getTransactionsByEmail(String email) {

        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {
            return expenseRepository.findByUser(credential.get().getUser());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Expense> getTransactionsByDate(LocalDate date) {
        return expenseRepository.findByDate(date);
    }

    @Override
    public List<Expense> getTransactionsByDate(LocalDate date, String email) {
        return (List<Expense>) transactionFilter.filterByEmail(getTransactionsByDate(date), email);
    }

    @Override
    public List<Expense> getTransactionsByMonth(Month month) {
        return expenseRepository.filterByMonth(month.getValue());
    }

    @Override
    public List<Expense> getTransactionsByMonth(Month month, String email) {
        return (List<Expense>) transactionFilter.filterByEmail(getTransactionsByMonth(month), email);
    }

    @Override
    public List<Expense> getLastWeekTransactions() {
        return expenseRepository.findLastWeek();
    }

    @Override
    public List<Expense> getLastWeekTransactions(String email) {
        return (List<Expense>) transactionFilter.filterByEmail(getLastWeekTransactions(), email);
    }

    @Override
    public List<Expense> getLastMonthTransactions() {
        return expenseRepository.findLastMonth();
    }

    @Override
    public List<Expense> getLastMonthTransactions(String email) {
        return (List<Expense>) transactionFilter.filterByEmail(getLastMonthTransactions(), email);
    }

    @Override
    public List<Expense> getYearIntervalTransactions(int start, int end) {
        return expenseRepository.filterByYearInterval(start, end);
    }

    @Override
    public List<Expense> getYearIntervalTransactions(String email, int start, int end) {
        return (List<Expense>) transactionFilter.filterByEmail(getYearIntervalTransactions(start, end), email);
    }

    public List<Expense> getTransactions() {
        return expenseRepository.findAll();
    }

    public Expense getTransactionById(long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteTransactionById(long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public boolean belongsToUser(IMoneyTransaction transaction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            if(authentication.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals("ADMIN"))) {

                Optional<Credential> credential = credentialRepository.findByEmail(userDetails.getUsername());
                if(credential.isEmpty()) throw new UserNotFoundException("The user has not been found");
                Optional<User> user = userRepository.findById(credential.get().getUser().getUserUuid());
                if(user.isEmpty()) throw new UserNotFoundException("The user has not been found");

                return user.get().getExpenses().contains((Expense) transaction);

            }

        }

        throw new UserNotAuthenticatedException("You are not authenticated");
    }

}