package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IMoneyTransaction;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface ITransactionService {

    void createOrUpdate(IMoneyTransaction transaction);
    List<? extends IMoneyTransaction> getTransactions();
    List<? extends IMoneyTransaction> getTransactionsByEmail(String email);
    List<? extends IMoneyTransaction> getTransactionsByDate(LocalDate date);
    List<? extends IMoneyTransaction> getTransactionsByMonth(Month month);
    IMoneyTransaction getTransactionById(long id);
    void deleteTransactionById(long it);

}
