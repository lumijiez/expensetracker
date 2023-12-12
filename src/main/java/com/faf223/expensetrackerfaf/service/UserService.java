package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.config.JwtService;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationResponse;
import com.faf223.expensetrackerfaf.dto.UserUpdateDTO;
import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.Role;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.security.PersonDetails;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final JwtService jwtService;

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public AuthenticationResponse updateUser(UserUpdateDTO userUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = getUserByEmail(userDetails.getUsername());
            Optional<Credential> credential = credentialRepository.findByUser(user);

            if (credential.isPresent()) {

                Credential credentialToUpdate = credential.get();

                if (userUpdate.getUsername() != null)
                    user.setUsername(userUpdate.getUsername());
                if (userUpdate.getFirstname() != null)
                    user.setFirstName(userUpdate.getFirstname());
                if (userUpdate.getLastname() != null)
                    user.setLastName(userUpdate.getLastname());
                if (userUpdate.getEmail() != null)
                    credentialToUpdate.setEmail(userUpdate.getEmail());

                userRepository.save(user);
                credentialRepository.save(credentialToUpdate);

                UserDetails details = new PersonDetails(credentialToUpdate);
                String jwtToken = jwtService.generateToken(details);
                String refreshToken = jwtService.generateRefreshToken(details);

                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        throw new UserNotFoundException("User not found!");
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