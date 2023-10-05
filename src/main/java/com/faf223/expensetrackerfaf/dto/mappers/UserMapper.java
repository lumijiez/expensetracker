package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.UserCreationDTO;
import com.faf223.expensetrackerfaf.dto.UserDTO;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public UserDTO toDto(User user) {
        return new UserDTO(user.getUuid(), user.getName(), user.getSurname(), user.getUsername());
    }

    public User toUser(UserCreationDTO userDTO) {
        return userService.getUserById(userDTO.getUuid());
    }

}
