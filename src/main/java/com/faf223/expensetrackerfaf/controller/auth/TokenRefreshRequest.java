package com.faf223.expensetrackerfaf.controller.auth;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
