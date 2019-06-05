package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.mapper.TodoMapper;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    private TodoService todoService;
    private TodoMapper todoMapper;
    private CurrentUser currentUser;

    @Autowired
    public TodoController(TodoService todoService, TodoMapper todoMapper, CurrentUser currentUser) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
        this.currentUser = currentUser;
    }

    @GetMapping("/todos")
    public List<TodoDTO> getAllTodos() {
        return todoMapper.toDto(todoService.findAll());
    }

    @GetMapping("/todos/{id}")
    public TodoDTO getById(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findById(id));
    }

    @GetMapping("/user/todos")
    public List<TodoDTO> getCurrentUserTodos() {
        return todoMapper.toDto(todoService.findAllByUserUsername(currentUser.getUser().getUsername()));
    }

    @GetMapping("/users/{username}/todos")
    public List<TodoDTO> getUserTodos(@PathVariable String username) {
        return todoMapper.toDto(todoService.findAllByUserUsername(username));
    }

}
