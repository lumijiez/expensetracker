package com.faf223.expensetrackerfaf.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetHelloWorld {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }
}
