package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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