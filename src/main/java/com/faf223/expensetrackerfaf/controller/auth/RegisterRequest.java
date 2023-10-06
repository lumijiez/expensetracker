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

    private String firstname;  // Change field name to match JSON
    private String lastname;   // Change field name to match JSON
    private String username;   // Change field name to match JSON
    private String email;      // Change field name to match JSON
    private String password;
    private Role role;
}
