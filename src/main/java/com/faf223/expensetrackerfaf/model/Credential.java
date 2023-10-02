package com.faf223.expensetrackerfaf.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "credentials")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private User user;

    private String email;
    private String password;
}

