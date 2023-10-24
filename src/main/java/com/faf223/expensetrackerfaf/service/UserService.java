package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
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
}