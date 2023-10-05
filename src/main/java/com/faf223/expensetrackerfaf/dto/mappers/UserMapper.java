package com.faf223.expensetrackerfaf.dto.mappers;

import com.faf223.expensetrackerfaf.dto.UserCreationDTO;
import com.faf223.expensetrackerfaf.dto.UserDTO;
import com.faf223.expensetrackerfaf.model.User;
import com.faf223.expensetrackerfaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getUsername());
    }

    public User toUser(UserCreationDTO userDTO) {
      
        User user = new User();
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        return user;
    }

}
