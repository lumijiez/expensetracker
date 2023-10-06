package com.faf223.expensetrackerfaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String surname;
    private String username;

}
