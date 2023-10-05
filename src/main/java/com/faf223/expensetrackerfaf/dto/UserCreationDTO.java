package com.faf223.expensetrackerfaf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreationDTO {
    private String uuid;
    private String name;
    private String surname;
    private String username;
}
