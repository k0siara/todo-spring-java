package com.patrykkosieradzki.todo.api.controller;

import com.patrykkosieradzki.todo.api.PageableDefaults;
import com.patrykkosieradzki.todo.api.entity.ApiResponse;
import com.patrykkosieradzki.todo.api.exception.TodoNotFoundException;
import com.patrykkosieradzki.todo.app.security.CurrentUser;
import com.patrykkosieradzki.todo.backend.dto.TodoDTO;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.mapper.TodoMapper;
import com.patrykkosieradzki.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TodoDTO> getAllTodos(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return todoMapper.toDto(todoService.findAll(pageable));
    }

    @GetMapping("/todos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TodoDTO getById(@PathVariable Long id) {
        return todoMapper.toDto(todoService.findById(id));
    }

    @GetMapping("/user/todos")
    @PreAuthorize("isAuthenticated()")
    public List<TodoDTO> getCurrentUserTodos(@PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {
        return todoMapper.toDto(todoService.findAllByUserUsername(currentUser.getUser().getUsername(), pageable));
    }

    @GetMapping("/user/todos/{id}")
    @PreAuthorize("isAuthenticated()")
    public TodoDTO getCurrentUserTodoById(@PathVariable Long id) {
        Todo todo = currentUser.getUser().getTodos()
                .stream().filter(t -> t.getId().equals(id)).findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        return todoMapper.toDto(todo);
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or #username == authentication.principal.username)")
    public List<TodoDTO> getUserTodos(
            @PathVariable String username,
            @PageableDefaults(minSize = 50, maxSize = 50, size = 50) Pageable pageable) {

        return todoMapper.toDto(todoService.findAllByUserUsername(username, pageable));
    }

    @PostMapping("/todos")
    @PreAuthorize("isAuthenticated()")
    public TodoDTO postTodo(@RequestBody TodoDTO dto) {
        Todo todo = todoMapper.toEntity(dto);
        todo.setUser(currentUser.getUser());

        return todoMapper.toDto(todoService.save(todo));
    }

    @PostMapping("/user/todos")
    @PreAuthorize("isAuthenticated()")
    public TodoDTO postCurrentUserTodo(@RequestBody TodoDTO dto) {
        Todo todo = todoMapper.toEntity(dto);
        todo.setUser(currentUser.getUser());

        return todoMapper.toDto(todoService.save(todo));
    }

    @PatchMapping("/todos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TodoDTO patchTodoById(@RequestBody TodoDTO todoDTO, @PathVariable Long id) {
        Todo todo = todoService.findById(id);

        todo.setText(todoDTO.getText() != null ? todoDTO.getText() : todo.getText());
        todo.setIsDone(todoDTO.getIsDone() != null ? todoDTO.getIsDone() : todo.getIsDone());
        todo.setTimestamp(todoDTO.getTimestamp() != null ? todoDTO.getTimestamp() : todo.getTimestamp());

        return todoMapper.toDto(todoService.update(todo));
    }

    @PatchMapping("/user/todos/{id}")
    @PreAuthorize("isAuthenticated()")
    public TodoDTO patchCurrentUserTodoById(@RequestBody TodoDTO todoDTO, @PathVariable Long id) {
        Todo todo = currentUser.getUser().getTodos()
                .stream().filter(t -> t.getId().equals(id)).findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todo.setText(todoDTO.getText() != null ? todoDTO.getText() : todo.getText());
        todo.setIsDone(todoDTO.getIsDone() != null ? todoDTO.getIsDone() : todo.getIsDone());
        todo.setTimestamp(todoDTO.getTimestamp() != null ? todoDTO.getTimestamp() : todo.getTimestamp());

        return todoMapper.toDto(todoService.update(todo));
    }

    @DeleteMapping("/todos/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        todoService.delete(todo);

        return ok(new ApiResponse("Todo #" + id + " removed"));
    }

    @DeleteMapping("/user/todos/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteCurrentUserTodoById(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        todoService.delete(todo);

        return ok(new ApiResponse("User todo #" + id + " removed"));
    }

}
