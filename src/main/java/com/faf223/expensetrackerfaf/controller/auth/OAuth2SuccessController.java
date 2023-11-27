package com.faf223.expensetrackerfaf.controller.auth;

import com.faf223.expensetrackerfaf.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2SuccessController {

    private final AuthenticationService jwtService;

    @GetMapping()
    public AuthenticationResponse getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {

        AuthenticationResponse response = jwtService.register(oAuth2User);
        System.out.println("Response: " + response);

        return response;
    }
}