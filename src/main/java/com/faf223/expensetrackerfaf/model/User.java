package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @Column(table = "users", name = "user_uuid")
    private String uuid;

    private String name;
    private String surname;
    private String username;
}
