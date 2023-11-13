package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
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

    @Override
    public List<Income> getTransactionsByDate(LocalDate date) {
        return incomeRepository.findByDate(date);
    }

    // TODO: store transaction month in a separate field in the DB and change this logic
    @Override
    public List<Income> getTransactionsByMonth(Month month) {
        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        return incomeRepository.findByDateBetween(startOfMonth, endOfMonth);
    }

    public Income getTransactionById(long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public void deleteTransactionById(long id) {
        incomeRepository.deleteById(id);
    }
}
