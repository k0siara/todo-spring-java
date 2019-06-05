package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found by id"));
    }

    public Todo save(Todo todo) {
        todoRepository.save(todo);
        return findById(todo.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public List<Todo> findAllByUserUsername(String username) {
        return todoRepository.findAllByUserUsername(username);
    }

    public Todo update(Todo todo) {
        todoRepository.update(todo);
        return findById(todo.getId());
    }

    public void deleteByDone(User user, boolean done) {
        todoRepository.deleteByDone(user, done);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}
