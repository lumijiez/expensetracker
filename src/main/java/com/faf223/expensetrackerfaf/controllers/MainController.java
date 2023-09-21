package com.faf223.expensetrackerfaf.controllers;

import com.faf223.expensetrackerfaf.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/users/get/{id}")
    public User getUser(@PathVariable int id) {
        return new User(id, "Test", null, null, null, null, null, null);
    }

}
