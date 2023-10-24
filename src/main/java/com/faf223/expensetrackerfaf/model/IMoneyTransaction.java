package com.faf223.expensetrackerfaf.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IMoneyTransaction {

    Long getId();
    LocalDate getDate();
    User getUser();
    BigDecimal getAmount();
    IMoneyTransactionCategory getCategory();

}
