package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.ExpenseCreationDTO;
import com.faf223.expensetrackerfaf.dto.ExpenseDTO;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    private final ExpenseService expenseService;
    private final UserMapper userMapper;

    @Autowired
    public ExpenseMapper(ExpenseService expenseService, UserMapper userMapper) {
        this.expenseService = expenseService;
        this.userMapper = userMapper;
    }

    public ExpenseDTO toDto(Expense expense) {
        return new ExpenseDTO(expense.getId(), userMapper.toDto(expense.getUser()),
                expense.getCategory(), expense.getDate(), expense.getAmount());
    }

    public Expense toExpense(ExpenseCreationDTO expenseDTO) {
        Expense expense = expenseService.getExpenseById(expenseDTO.getExpenseId());
        if(expense == null) return new Expense(expenseDTO.getExpenseId(), expenseDTO.getUser(),
                expenseDTO.getExpenseCategory(), expenseDTO.getDate(), expenseDTO.getAmount());
        return expense;
    }

}
