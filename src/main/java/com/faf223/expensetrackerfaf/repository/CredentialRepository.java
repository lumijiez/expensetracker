package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByEmail(String email);

    Optional<Credential> findByUser(User user);
}