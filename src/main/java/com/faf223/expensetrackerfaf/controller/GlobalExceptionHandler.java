package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.util.errors.ErrorResponse;
import com.faf223.expensetrackerfaf.util.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleDoesNotBelongException(TransactionDoesNotBelongToTheUserException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleTransactionNotCreatedException(TransactionNotCreatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleTransactionsNotFoundException(TransactionsNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleTransactionNotUpdatedException(TransactionNotUpdatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleUserNotAuthenticatedException(UserNotAuthenticatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleUserNotCreatedException(UserNotCreatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleRequiredParamMissingException(RequiredParamMissingException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleWrongParamValueException(WrongParamValueException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
