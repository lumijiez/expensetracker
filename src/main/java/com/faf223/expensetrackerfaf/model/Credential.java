package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "credentials")
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private User user;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Credential(User user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;

        this.role = Role.ROLE_USER;
    }
}

