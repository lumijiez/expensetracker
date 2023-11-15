package com.faf223.expensetrackerfaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "incomes")
public class Income implements IMoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private IncomeCategory category;

    @NotNull
    private LocalDate date;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    public Income(IncomeCategory incomeCategory, LocalDate date, BigDecimal amount) {
        this.category = incomeCategory;
        this.date = date;
        this.amount = amount;
    }
}
