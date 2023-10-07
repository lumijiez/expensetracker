package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.UserCreationDTO;
import com.faf223.expensetrackerfaf.dto.UserDTO;
import com.faf223.expensetrackerfaf.dto.mappers.UserMapper;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PatchMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserCreationDTO userDTO,
                                              BindingResult bindingResult) {
        User user = userMapper.toUser(userDTO);
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
            return ResponseEntity.ok(userMapper.toDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userUuid) {
        User user = userService.getUserById(userUuid);
        if (user != null) return ResponseEntity.ok(userMapper.toDto(user));
        else return ResponseEntity.notFound().build();
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<UserDTO>> getAllUsers() {
        ArrayList<User> users = new ArrayList<>(userService.getUsers());

        return ResponseEntity.ok(userMapper.toDto(users));
    }
}

