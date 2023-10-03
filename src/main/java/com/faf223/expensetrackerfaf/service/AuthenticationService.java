package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.config.JwtService;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationRequest;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationResponse;
import com.faf223.expensetrackerfaf.controller.auth.RegisterRequest;
import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.security.PersonDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, CredentialRepository credentialRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = repository;
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .build();
        System.out.println(user);
        userRepository.save(user);
        Credential credential = new Credential(user, request.getEmail(), passwordEncoder.encode(request.getPassword()));
        credentialRepository.save(credential);

        String jwtToken = jwtService.generateToken(new PersonDetails(credential));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

         Credential credential = credentialRepository.findByEmail(request.getEmail()).orElseThrow((() -> new UsernameNotFoundException("User not found")));

        String jwtToken = jwtService.generateToken(new PersonDetails(credential));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
