package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // TODO: store transaction month in a separate field in the DB and change this logic
    @Override
    public List<Expense> getTransactionsByMonth(Month month) {
        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        return expenseRepository.findByDateBetween(startOfMonth, endOfMonth);
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
}