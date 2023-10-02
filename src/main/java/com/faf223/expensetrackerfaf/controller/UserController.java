package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        if (!users.isEmpty()) return ResponseEntity.ok(users);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<User> createNewUser(@RequestBody User user,
                                                  BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.createOrUpdateUser(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                               BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.createOrUpdateUser(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<User> getUser(@PathVariable String userUuid) {
        User user = userService.getUserById(userUuid);
        if (user != null) return ResponseEntity.ok(user);
        else return ResponseEntity.notFound().build();
    }
}

