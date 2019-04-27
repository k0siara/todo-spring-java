package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.NewUserDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private UserService userService;

    public User userDtoToEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        return userService.findById(userDTO.getId());
    }

    public abstract List<User> userDtosToEntities(List<UserDTO> userDTOs);

    public abstract UserDTO userToDto(User user);

    public abstract List<UserDTO> usersToDtos(List<User> users);


    public User newUserDtoToEntity(NewUserDTO newUserDTO) {
        if ( newUserDTO == null ) {
            return null;
        }

        return userService.findById(newUserDTO.getId());
    }

    public abstract List<User> newUserDtosToEntities(List<NewUserDTO> newUserDTOs);

    public abstract NewUserDTO userToNewUserDto(User user);

    public abstract List<NewUserDTO> usersToNewUserDtos(List<User> users);

}

