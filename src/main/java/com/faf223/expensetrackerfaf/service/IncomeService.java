package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncomeService implements ITransactionService {

    private final IncomeRepository incomeRepository;
    private final CredentialRepository credentialRepository;

    public void createOrUpdate(IMoneyTransaction income) {
        incomeRepository.save((Income) income);
    }

    public List<Income> getTransactions() {
        return incomeRepository.findAll();
    }

    public List<Income> getTransactionsByEmail(String email) {

        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {
            return incomeRepository.findByUser(credential.get().getUser());
        }

        return new ArrayList<>();
    }

    public Income getTransactionById(long id) {
        return incomeRepository.findById(id).orElse(null);
    }
}
