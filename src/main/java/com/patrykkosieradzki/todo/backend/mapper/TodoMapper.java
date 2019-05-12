package com.patrykkosieradzki.todo.backend.mapper;

import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoDTO toDto(Todo todo);

    List<TodoDTO> toDto(List<Todo> todos);

}
