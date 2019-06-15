package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.api.exception.ApiError;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.app.security.SecurityUtils;
import com.patrykkosieradzki.todo.app.security.annotations.IsAdmin;
import com.patrykkosieradzki.todo.app.security.annotations.IsAdminOrCurrentUser;
import com.patrykkosieradzki.todo.app.security.annotations.IsAuthenticated;
import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.mapper.TodoMapper;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @IsAdmin
    @GetMapping("/todos")
    public List<TodoDTO> getAllTodos(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return todoMapper.toDto(todoService.findAll(pageable));
    }

    @IsAdmin
    @GetMapping("/todos/{id}")
    public TodoDTO getById(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findById(id));
    }

    @IsAuthenticated
    @GetMapping("/user/todos")
    public List<TodoDTO> getCurrentUserTodos(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return todoMapper.toDto(todoService.findAllByUserUsername(currentUser.getUser().getUsername(), pageable));
    }

    @IsAuthenticated
    @GetMapping("/user/todos/{id}")
    public ResponseEntity<Object> getCurrentUserTodoById(@PathVariable Long id) {
        if (currentUser.getUser().getTodos().stream().anyMatch(todo -> todo.getId().equals(id))) {
            return ok().body(todoMapper.toDto(todoService.findById(id)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    "Todo not found"
            ));
        }
    }

    @IsAdminOrCurrentUser
    @GetMapping("/users/{username}/todos")
    public List<TodoDTO> getUserTodos(
            @PathVariable String username,
            @PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return todoMapper.toDto(todoService.findAllByUserUsername(username, pageable));
    }

    @IsAdmin
    @PostMapping("/todos")
    public TodoDTO postTodo(@RequestBody TodoDTO dto) {
        return todoMapper.toDto(todoService.save(todoMapper.toEntity(dto)));
    }

    @PostMapping("/user/todos")
    @PreAuthorize("isAuthenticated() and #dto.user == authentication.principal.username")
    public TodoDTO postCurrentUserTodo(@RequestBody TodoDTO dto) {
        return todoMapper.toDto(todoService.save(todoMapper.toEntity(dto)));
    }

    @IsAdmin
    @PatchMapping("/todos/{id}")
    public TodoDTO patchTodoById(@RequestBody Todo todo, @PathVariable Long id) {
        return todoMapper.toDto(todoService.update(todo, id));
    }


    @PatchMapping("/user/todos/{id}")
    @PreAuthorize("isAuthenticated() and #todo.user.username == authentication.principal.username")
    public TodoDTO patchCurrentUserTodoById(@RequestBody Todo todo, @PathVariable Long id) {
        return todoMapper.toDto(todoService.update(todo, id));
    }

    @IsAdmin
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        todoService.delete(todo);

        return ok(new ApiResponse("Todo #" + id + " removed"));
    }

    @IsAuthenticated
    @DeleteMapping("/user/todos/{id}")
    public ResponseEntity<Object> deleteCurrentUserTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);

        if (todo.getUser().getUsername().equals(SecurityUtils.getUsername())) {
            todoService.delete(todo);
            return ok(new ApiResponse("User todo #" + id + " removed"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiError(
                    HttpStatus.FORBIDDEN.value(),
                    "Forbidden"
            ));
        }
    }

}
