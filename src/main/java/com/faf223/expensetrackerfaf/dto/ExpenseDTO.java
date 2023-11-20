package com.faf223.expensetrackerfaf.dto;

import com.faf223.expensetrackerfaf.model.ExpenseCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseDTO {
    @NotNull(message = "ID must not be null")
    private long expenseId;

    @NotNull(message = "User must not be null")
    private UserDTO userDTO;

    @NotNull(message = "Category must not be null")
    private ExpenseCategory expenseCategory;

    @NotNull(message = "Date must not be null")
    private LocalDate date;

    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;
}