package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    public List<Income> getIncomesByUserId(String userUuid) {
        return incomeRepository.findByUserUserUuid(userUuid);
    }
}
