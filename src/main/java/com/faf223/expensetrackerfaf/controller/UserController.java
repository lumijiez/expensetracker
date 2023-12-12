package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.controller.auth.ChangePasswordRequest;
import com.faf223.expensetrackerfaf.dto.UserCreationDTO;
import com.faf223.expensetrackerfaf.dto.UserDTO;
import com.faf223.expensetrackerfaf.dto.mappers.UserMapper;
import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.CredentialRepository;
import com.faf223.expensetrackerfaf.service.AuthenticationService;
import com.faf223.expensetrackerfaf.service.UserService;
import com.faf223.expensetrackerfaf.util.errors.ErrorResponse;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotCreatedException;
import com.faf223.expensetrackerfaf.util.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final CredentialRepository credentialRepository;
    private final AuthenticationService authenticationService;

    @PatchMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserCreationDTO userDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new UserNotCreatedException(ErrorResponse.from(bindingResult).getMessage());

        User user = userMapper.toUser(userDTO);

        if (!bindingResult.hasErrors()) {

            userService.updateUser(user);
            return ResponseEntity.ok(userMapper.toDto(user));

        } else {
            throw new UserNotFoundException("The user has not been found");
        }
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@RequestBody ChangePasswordRequest password) {

        System.out.println("Hi");

        authenticationService.updatePassword(password.getPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-user-data")
    public ResponseEntity<Map<String, String>> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.getUserByEmail(userDetails.getUsername());
            Optional<Credential> credential = credentialRepository.findByUser(user);

            if (credential.isPresent()) {
                Map<String, String> userData = new HashMap<>();
                userData.put("firstname", user.getFirstName());
                userData.put("lastname", user.getLastName());
                userData.put("username", user.getUsername());
                userData.put("userrole", credential.get().getRole().toString()); // Assuming UserRole is an enum

                return ResponseEntity.ok(userData);
            }
        }
        throw new UserNotFoundException("The user has not been found");
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<UserDTO>> getAllUsers() {
        ArrayList<User> users = new ArrayList<>(userService.getUsers());

        return ResponseEntity.ok(userMapper.toDto(users));
    }

    @GetMapping("/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {

        userService.deleteByUsername(username);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

