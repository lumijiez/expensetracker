package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.config.JwtService;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationRequest;
import com.faf223.expensetrackerfaf.controller.auth.AuthenticationResponse;
import com.faf223.expensetrackerfaf.controller.auth.RegisterRequest;
import com.faf223.expensetrackerfaf.controller.auth.TokenRefreshRequest;
import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import com.faf223.expensetrackerfaf.security.PersonDetails;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotAuthenticatedException;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordGenerator passwordGenerator;

    public AuthenticationResponse register(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .username(request.getUsername())
                .build();

        userRepository.save(user);
        Credential credential = new Credential(user, request.getEmail(), passwordEncoder.encode(request.getPassword()));
        credentialRepository.save(credential);

        UserDetails userDetails = new PersonDetails(credential);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        System.out.println(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse register(OAuth2User oAuth2User) {
        String userEmail = oAuth2User.getAttribute("email");

        // Check if the user is already registered
        Optional<Credential> existingCredential = credentialRepository.findByEmail(userEmail);
        if (existingCredential.isPresent()) {
            UserDetails userDetails = new PersonDetails(existingCredential.get());
            String jwtToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }


        String givenName = oAuth2User.getAttribute("given_name");
        String familyName = oAuth2User.getAttribute("family_name");
        String email = oAuth2User.getAttribute("email");

        User user = User.builder()
                .firstName(givenName)
                .lastName(familyName)
                .username(email)
                .build();

        String randomPassword = passwordGenerator.generateRandomPassword(8);

        user.setPassword(passwordEncoder.encode(randomPassword));

        userRepository.save(user);
        Credential credential = new Credential(user, email, passwordEncoder.encode(randomPassword));
        credentialRepository.save(credential);

        UserDetails userDetails = new PersonDetails(credential);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        System.out.println("New user: " + user);
        System.out.println("New credentials: " + credential);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Credential credential = credentialRepository.findByEmail(request.getEmail()).orElseThrow((() -> new UserNotFoundException("User not found")));

        UserDetails userDetails = new PersonDetails(credential);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshAccessToken(TokenRefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        Optional<Credential> credential = credentialRepository.findByEmail(jwtService.extractUsername(refreshToken));
        if (credential.isPresent()) {
            UserDetails userDetails = new PersonDetails(credential.get());

            String jwtToken = jwtService.generateToken(userDetails);
            System.out.println(jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new UserNotAuthenticatedException("Invalid or expired refresh token");
        }
    }

}
