package com.faf223.expensetrackerfaf.util;

import com.faf223.expensetrackerfaf.model.User;

public interface IMoneyTransaction {

    User getUser();
    int getAmount();
    String getCategory();

}
