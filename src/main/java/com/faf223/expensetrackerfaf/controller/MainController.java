package com.faf223.expensetrackerfaf.controller;

import com.faf223.expensetrackerfaf.model.Expense;
import com.faf223.expensetrackerfaf.model.Income;
import com.faf223.expensetrackerfaf.model.Role;
import com.faf223.expensetrackerfaf.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/users/get/{id}")
    public User getUser(@PathVariable int id) {
        return new User(id, "Test", null, null, null, null, null);
    }

    @PostMapping("/users/set/{id}/name")
    public String setName(@PathVariable int id,
                          @RequestParam("name") String name) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/email")
    public String setEmail(@PathVariable int id,
                           @RequestParam("email") String email) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/login")
    public String setLogin(@PathVariable int id,
                           @RequestParam("login") String login) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/password")
    public String setPassword(@PathVariable int id,
                              @RequestParam("password") String password) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/role")
    public String setRole(@PathVariable int id,
                          @RequestParam("role") Role role) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/expenses")
    public String setExpenses(@PathVariable int id,
                              @RequestParam("expenses") List<Expense> expenses) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

    @PostMapping("/users/set/{id}/incomes")
    public String setIncomes(@PathVariable int id,
                             @RequestParam("incomes")List<Income> incomes) {
        throw new UnsupportedOperationException("Waiting for the DB.");
    }

}