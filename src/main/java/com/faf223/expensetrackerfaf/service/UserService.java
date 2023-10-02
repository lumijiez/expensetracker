package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(String userUuid) {
        return userRepository.findById(userUuid).orElse(null);
    }
}