package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialRepository credentialRepository;

    public Optional<Credential> findByEmail(String email) {
        return credentialRepository.findByEmail(email);
    }

}
