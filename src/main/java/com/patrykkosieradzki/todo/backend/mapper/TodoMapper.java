package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TodoMapper {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mappings({
            @Mapping(target = "user", source = "user.username")
    })
    public abstract TodoDTO toDto(Todo todo);

    public abstract List<TodoDTO> toDto(List<Todo> todos);

    public Todo toEntity(TodoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Todo todo = new Todo();

        User user = userService.findByUsername(dto.getUser());

        todo.setUser(user);
        todo.setText( dto.getText() );
        todo.setDone( dto.isDone() );
        todo.setTimestamp( dto.getTimestamp() );

        return todo;
    }
}
