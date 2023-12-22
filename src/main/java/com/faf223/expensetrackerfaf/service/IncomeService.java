package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.IMoneyTransaction;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.util.TransactionFilter;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotAuthenticatedException;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;
    private final TransactionFilter transactionFilter;

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

    @Override
    @SuppressWarnings("unchecked")
    public List<Income> getTransactionsByDate(LocalDate date, String email) {
        return (List<Income>) transactionFilter.filterByEmail(getTransactionsByDate(date), email);
    }

    @Override
    public List<Income> getTransactionsByMonth(Month month) {
        return incomeRepository.filterByMonth(month.getValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Income> getTransactionsByMonth(Month month, String email) {
        return (List<Income>) transactionFilter.filterByEmail(getTransactionsByMonth(month), email);
    }

    @Override
    public List<Income> getLastWeekTransactions() {
        return incomeRepository.findLastWeek();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Income> getLastWeekTransactions(String email) {
        return (List<Income>) transactionFilter.filterByEmail(getLastWeekTransactions(), email);
    }

    @Override
    public List<Income> getLastMonthTransactions() {
        return incomeRepository.findLastMonth();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Income> getLastMonthTransactions(String email) {
        return (List<Income>) transactionFilter.filterByEmail(getLastMonthTransactions(), email);
    }

    @Override
    public List<Income> getYearIntervalTransactions(int start, int end) {
        return incomeRepository.filterByYearInterval(start, end);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Income> getYearIntervalTransactions(String email, int start, int end) {
        return (List<Income>) transactionFilter.filterByEmail(getYearIntervalTransactions(start, end), email);
    }

    public Income getTransactionById(long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    public void deleteTransactionById(long id) {
        incomeRepository.deleteById(id);
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

                return user.get().getIncomes().contains((Income) transaction);

            }

            return true;

        }

        throw new UserNotAuthenticatedException("You are not authenticated");
    }
}
