package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    @Mappings({
            @Mapping(target = "user", source = "user.username")
    })
    TodoDTO toDto(Todo todo);

    List<TodoDTO> toDto(List<Todo> todos);

}
