package com.faf223.expensetrackerfaf.util.exceptions;

public class TransactionsNotFoundException extends RuntimeException {
    public TransactionsNotFoundException(String message) {
        super(message);
    }
}
