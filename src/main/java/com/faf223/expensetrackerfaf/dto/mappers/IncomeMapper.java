package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.IncomeCreationDTO;
import com.faf223.expensetrackerfaf.dto.IncomeDTO;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.service.IncomeCategoryService;
import com.faf223.expensetrackerfaf.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncomeMapper {

    private final IncomeService incomeService;
    private final IncomeCategoryService incomeCategoryService;
    private final UserMapper userMapper;

    @Autowired
    public IncomeMapper(IncomeService incomeService, IncomeCategoryService incomeCategoryService, UserMapper userMapper) {
        this.incomeService = incomeService;
        this.incomeCategoryService = incomeCategoryService;
        this.userMapper = userMapper;
    }

    public IncomeDTO toDto(Income income) {
        return new IncomeDTO(income.getId(), userMapper.toDto(income.getUser()),
                income.getCategory(), income.getDate(), income.getAmount());
    }

    public Income toIncome(IncomeCreationDTO incomeDTO) {
        return new Income(incomeCategoryService.getCategoryById(incomeDTO.getIncomeCategory()), LocalDate.now(), incomeDTO.getAmount());
    }

}