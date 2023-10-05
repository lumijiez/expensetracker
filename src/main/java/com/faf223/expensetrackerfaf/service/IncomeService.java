package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public void createOrUpdateIncome(Income income) {
        incomeRepository.save(income);
    }

    public List<Income> getIncomes() {
        return incomeRepository.findAll();
    }

    public List<Income> getIncomesByUserId(String userUuid) {
        return incomeRepository.findByUserUuid(userUuid);
    }

    public Income getIncomeById(long id) {
        return incomeRepository.findById(id).orElse(null);
    }
}
