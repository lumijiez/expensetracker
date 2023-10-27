package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IMoneyTransaction;

import java.util.List;

public interface ITransactionService {

    void createOrUpdate(IMoneyTransaction transaction);
    List<? extends IMoneyTransaction> getTransactions();
    List<? extends IMoneyTransaction> getTransactionsByEmail(String email);
    IMoneyTransaction getTransactionById(long id);
    void deleteTransactionById(long it);

}
