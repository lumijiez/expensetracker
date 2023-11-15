package com.faf223.expensetrackerfaf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreationDTO {
    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    private String firstname;
    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    private String lastname;
    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    private String username;
    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
