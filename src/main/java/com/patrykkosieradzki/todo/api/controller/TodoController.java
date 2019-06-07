package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.mapper.TodoMapper;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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

    @PatchMapping("/todos/{id}")
    public TodoDTO patchTodoById(@RequestBody Todo todo, @PathVariable Long id) {
        return todoMapper.toDto(todoService.update(todo, id));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        todoService.delete(todo);

        return ok(new ApiResponse("Todo removed"));
    }

    @GetMapping("/user/todos")
    public List<TodoDTO> getCurrentUserTodos() {
        return todoMapper.toDto(todoService.findAllByUserUsername(currentUser.getUser().getUsername()));
    }

    @GetMapping("/user/todos/{id}")
    public TodoDTO getCurrentUserTodoById(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findById(id));
    }

    @DeleteMapping("/user/todos/{id}")
    public ResponseEntity<Object> deleteCurrentUserTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        todoService.delete(todo);

        return ok(new ApiResponse("Todo removed"));
    }

    @GetMapping("/users/{username}/todos")
    public List<TodoDTO> getUserTodos(@PathVariable String username) {
        return todoMapper.toDto(todoService.findAllByUserUsername(username));
    }

}
