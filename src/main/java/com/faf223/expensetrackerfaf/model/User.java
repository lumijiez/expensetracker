package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class User {
    @Id
    @Column(table = "users", name = "user_uuid")
    private String uuid;

    private String name;
    private String surname;
    private String username;
}
