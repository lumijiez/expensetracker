package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.IncomeCreationDTO;
import com.faf223.expensetrackerfaf.dto.IncomeDTO;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    private final IncomeService incomeService;
    private final UserMapper userMapper;

    @Autowired
    public IncomeMapper(IncomeService incomeService, UserMapper userMapper) {
        this.incomeService = incomeService;
        this.userMapper = userMapper;
    }

    public IncomeDTO toDto(Income income) {
        return new IncomeDTO(income.getIncomeId(), userMapper.toDto(income.getUser()),
                income.getCategory(), income.getDate(), income.getAmount());
    }

    public Income toIncome(IncomeCreationDTO incomeDTO) {
        return incomeService.getIncomeById(incomeDTO.getIncomeId());
    }

}
