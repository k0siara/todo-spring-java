package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.AdminUserDTO;
import com.patrykkosieradzki.todo.backend.dto.AuthenticatedUserDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.entity.Role;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Mappings({
            @Mapping(target = "todos", expression = "java(user.getTodos().size())"),
    })
    public abstract UserDTO toUserDTO(User user);

    public abstract List<UserDTO> toUserDTO(List<User> users);

    @Mappings({
            @Mapping(target = "todos", expression = "java(user.getTodos().size())")
    })
    public abstract AuthenticatedUserDTO toAuthenticatedUserDTO(User user);

    public abstract List<AuthenticatedUserDTO> toAuthenticatedUserDTO(Collection<User> users);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "todos", expression = "java(user.getTodos().size())"),
            @Mapping(target = "roles", source = "user", qualifiedByName = "roles")
    })
    public abstract AdminUserDTO toAdminUserDTO(User user);

    public abstract List<AdminUserDTO> toAdminUserDTO(Collection<User> users);

    @Named("roles")
    List<String> getUserRoles(User user) {
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }
}

