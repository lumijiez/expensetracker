package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.IMoneyTransactionCategory;

import java.util.List;

public interface ICategoryService {

    List<? extends IMoneyTransactionCategory> getAllCategories();
    IMoneyTransactionCategory getCategoryById(long id);

}
