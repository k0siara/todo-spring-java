package com.patrykkosieradzki.todo.api.controller;

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
@RequestMapping("/api/todos")
public class TodoController {

    private TodoService todoService;
    private TodoMapper todoMapper;

    @Autowired
    public TodoController(TodoService todoService, TodoMapper todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @GetMapping
    public List<TodoDTO> getAllTodos() {
        return todoMapper.toDto(todoService.findAll());
    }

    @GetMapping("/{id}")
    public TodoDTO getById(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findById(id));
    }

    @GetMapping("/users/{id}/todos")
    public List<TodoDTO> getUserTodos(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findAllByUserId(id));
    }

}
