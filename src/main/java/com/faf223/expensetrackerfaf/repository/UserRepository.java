package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getUserByUserUuid(String userUuid);

    Optional<User> findByUsername(String username);
}
