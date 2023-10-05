package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.dto.UserCreationDTO;
import com.faf223.expensetrackerfaf.dto.UserDTO;
import com.faf223.expensetrackerfaf.dto.mappers.UserMapper;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
        if (!users.isEmpty()) return ResponseEntity.ok(users);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserCreationDTO userDTO,
                                                  BindingResult bindingResult) {
        User user = userMapper.toUser(userDTO);
        if (!bindingResult.hasErrors()) {
            userService.createOrUpdateUser(user);
            return ResponseEntity.ok(userMapper.toDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserCreationDTO userDTO,
                                               BindingResult bindingResult) {
        User user = userMapper.toUser(userDTO);
        if (!bindingResult.hasErrors()) {
            userService.createOrUpdateUser(user);
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
}

