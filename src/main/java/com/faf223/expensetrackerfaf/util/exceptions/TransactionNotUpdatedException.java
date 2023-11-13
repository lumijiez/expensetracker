package com.faf223.expensetrackerfaf.util.exceptions;

public class TransactionNotUpdatedException extends RuntimeException {
    public TransactionNotUpdatedException(String message) {
        super(message);
    }
}
