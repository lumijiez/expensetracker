package com.faf223.expensetrackerfaf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    @NotNull(message = "Name must not be null")
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotNull(message = "Surname must not be null")
    @NotEmpty(message = "Surname must not be empty")
    private String surname;
    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    private String username;
}
