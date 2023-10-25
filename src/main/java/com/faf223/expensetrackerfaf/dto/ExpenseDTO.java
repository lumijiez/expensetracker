package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    private long expenseId;
    private UserDTO userDTO;
    private ExpenseCategory expenseCategory;
    private LocalDate date;
    private BigDecimal amount;
}