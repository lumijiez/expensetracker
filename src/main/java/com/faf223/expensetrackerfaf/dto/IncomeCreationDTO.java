package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.faf223.expensetrackerfaf.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IncomeCreationDTO {
    private int incomeCategory;
    private BigDecimal amount;
}