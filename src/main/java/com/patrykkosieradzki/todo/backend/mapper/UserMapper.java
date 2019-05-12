package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        return userService.findById(userDTO.getId());
    }

    public abstract UserDTO toDto(User user);

    public abstract List<UserDTO> toDto(List<User> users);


}

