package com.faf223.expensetrackerfaf.controllers;

import entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return new User(id, "Test");
    }

}
