package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public List<Income> getIncomesByUserId(String userUuid) {
        return incomeRepository.findByUserUserUuid(userUuid);
    }
}
