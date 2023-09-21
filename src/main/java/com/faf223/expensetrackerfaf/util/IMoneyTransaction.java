package com.faf223.expensetrackerfaf.util;

import com.faf223.expensetrackerfaf.entities.User;

public interface IMoneyTransaction {

    User getUser();

    int getAmount();
    String getCategory();

}
