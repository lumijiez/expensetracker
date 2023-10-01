package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
}