package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.dto.UserDTO;
import com.patrykkosieradzki.todo.backend.mapper.TodoMapper;
import com.patrykkosieradzki.todo.backend.mapper.UserMapper;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import com.patrykkosieradzki.todo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private TodoService todoService;
    private UserMapper userMapper;
    private TodoMapper todoMapper;

    @Autowired
    public UserController(UserService userService, TodoService todoService, UserMapper userMapper, TodoMapper todoMapper) {
        this.userService = userService;
        this.todoService = todoService;
        this.userMapper = userMapper;
        this.todoMapper = todoMapper;
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userMapper.toDto(userService.findAll());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.findById(id));
    }

    @GetMapping("/{id}/todos")
    public List<TodoDTO> getUserTodos(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findAllByUserId(id));
    }


}
