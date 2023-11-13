package com.faf223.expensetrackerfaf.util.exceptions;

public class TransactionDoesNotBelongToTheUserException extends RuntimeException {
    public TransactionDoesNotBelongToTheUserException(String message) {
        super(message);
    }
}
