package com.faf223.expensetrackerfaf.util.exceptions;

public class RequiredParamMissingException extends RuntimeException {
    public RequiredParamMissingException(String message) {
        super(message);
    }
}
