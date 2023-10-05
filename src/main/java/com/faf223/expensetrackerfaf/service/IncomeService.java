package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public void createOrUpdateIncome(Income income) {
        incomeRepository.save(income);
    }

    public List<Income> getIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getIncomesByUserId(String userUuid) {

        Optional<User> user = userRepository.getUserByUserUuid(userUuid);
        if (user.isPresent()) {
            return incomeRepository.findByUser(user.get());
        }

        return new ArrayList<>();
    }

    public Income getIncomeById(long id) {
        return incomeRepository.findById(id).orElse(null);
    }
}
