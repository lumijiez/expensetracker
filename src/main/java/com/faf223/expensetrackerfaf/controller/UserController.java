package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userUuid}")
    public ResponseEntity<User> getUser(@PathVariable String userUuid) {
        // TODO: Create a DTO class that will be returned instead of User(password: null and uuid are returned inside of the user object)
        User user = userService.getUserById(userUuid);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

