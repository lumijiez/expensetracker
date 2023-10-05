package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IncomeDTO {
    private long incomeId;
    private UserDTO userDTO;
    private IncomeCategory category;
    private LocalDate date;
    private BigDecimal amount;
}
