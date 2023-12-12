package com.faf223.expensetrackerfaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    @Column(name = "surname")
    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @Column(name = "username")
    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    private String username;

    @Transient
//    @NotNull(message = "Password must not be null")
//    @NotEmpty(message = "Password must not be empty")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Income> incomes;

    @ManyToOne
    @JoinColumn(name = "family_id")
    @ToString.Exclude
    @JsonIgnore
    private Family family;
}
