package com.faf223.expensetrackerfaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDTO {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
}
