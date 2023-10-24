package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import com.faf223.expensetrackerfaf.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseCreationDTO {
    private int expenseCategory;
    private BigDecimal amount;
}