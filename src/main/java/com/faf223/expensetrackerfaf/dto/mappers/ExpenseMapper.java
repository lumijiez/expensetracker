package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.ExpenseCreationDTO;
import com.faf223.expensetrackerfaf.dto.ExpenseDTO;
import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.service.ExpenseCategoryService;
import com.faf223.expensetrackerfaf.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExpenseMapper {

    private final ExpenseCategoryService expenseCategoryService;
    private final UserMapper userMapper;

    public ExpenseDTO toDto(Expense expense) {
        return new ExpenseDTO(expense.getId(), userMapper.toDto(expense.getUser()),
                expense.getCategory(), expense.getDate(), expense.getAmount());
    }

    public Expense toExpense(ExpenseCreationDTO expenseDTO) {

        return new Expense(expenseCategoryService.getCategoryById(expenseDTO.getExpenseCategory()), LocalDate.now(), expenseDTO.getAmount());
    }

}