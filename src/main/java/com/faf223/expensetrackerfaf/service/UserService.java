package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userUuid) {
        return userRepository.findById(userUuid).orElse(null);
    }
}