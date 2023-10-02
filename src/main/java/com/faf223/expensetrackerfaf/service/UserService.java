package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createOrUpdateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userUuid) {
        return userRepository.findById(userUuid).orElse(null);
    }
}