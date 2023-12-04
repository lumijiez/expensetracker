package com.faf223.expensetrackerfaf.controller.auth;

import com.faf223.expensetrackerfaf.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("register");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        System.out.println("Refresh token!========================");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<AuthenticationResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        System.out.println("Refresh token!========================");
        return ResponseEntity.ok(service.refreshAccessToken(request));
    }
}
