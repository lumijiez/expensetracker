package com.faf223.expensetrackerfaf.controller.auth;


import com.faf223.expensetrackerfaf.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private Role role;
}
