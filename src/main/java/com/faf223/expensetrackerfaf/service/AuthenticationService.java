package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.config.JwtService;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationRequest;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationResponse;
import com.faf223.expensetrackerfaf.controller.auth.RegisterRequest;
import com.faf223.expensetrackerfaf.model.Role;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.security.PersonDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(new PersonDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
