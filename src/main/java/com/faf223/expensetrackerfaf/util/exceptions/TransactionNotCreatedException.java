package com.faf223.expensetrackerfaf.util.exceptions;

public class TransactionNotCreatedException extends RuntimeException {
    public TransactionNotCreatedException(String message) {
        super(message);
    }
}
