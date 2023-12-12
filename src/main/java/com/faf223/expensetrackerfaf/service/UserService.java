package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Role;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userUuid) {
        return userRepository.findById(userUuid).orElse(null);
    }

    public User getUserByEmail(String email) {
        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {
            Optional<User> user = userRepository.findById(credential.get().getUser().getUserUuid());
            return user.orElse(null);
        }
        return null;
    }

    @Transactional
    public void deleteByUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {

            Optional<Credential> credential = credentialRepository.findByUser(user.get());

            if (credential.isPresent()) {

                credentialRepository.deleteByEmail(credential.get().getEmail());
                userRepository.deleteByUsername(username);
            }
        }
    }

    public void promoteUser(String email) {
        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {

            System.out.println(email);
            Credential updatedCredential = credential.get();
            updatedCredential.setRole(Role.ROLE_ADMIN);
            credentialRepository.save(updatedCredential);
        }
    }

    public void demoteUser(String email) {
        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {

            System.out.println(email);
            Credential updatedCredential = credential.get();
            updatedCredential.setRole(Role.ROLE_USER);
            credentialRepository.save(updatedCredential);
        }
    }
}