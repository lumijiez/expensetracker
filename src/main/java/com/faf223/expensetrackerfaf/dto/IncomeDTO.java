package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.IncomeCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonIgnoreProperties({"incomeCategory"})
public class IncomeDTO {
    private long incomeId;
    private UserDTO userDTO;
    private IncomeCategory incomeCategory;
    private LocalDate date;
    private BigDecimal amount;
}