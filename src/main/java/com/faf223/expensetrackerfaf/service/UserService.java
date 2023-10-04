package com.faf223.expensetrackerfaf.service;

import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(String userUuid) {
        return userRepository.findById(userUuid).orElse(null);
    }
}