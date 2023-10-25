package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userUuid;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Transient
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Income> incomes;
}
