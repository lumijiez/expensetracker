package com.faf223.expensetrackerfaf.config;

import com.faf223.expensetrackerfaf.controller.auth.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleTokenExpiredException(TokenExpiredException ex) {
        return new ErrorResponse("Unauthorized", ex.getMessage());
    }
}
