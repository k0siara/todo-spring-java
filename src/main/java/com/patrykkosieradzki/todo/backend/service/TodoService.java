package com.patrykkosieradzki.todo.backend.service;

import com.patrykkosieradzki.todo.api.exception.TodoNotFoundException;
import com.patrykkosieradzki.todo.backend.entity.Todo;
import com.patrykkosieradzki.todo.backend.entity.User;
import com.patrykkosieradzki.todo.backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
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
    public List<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll();
    }

    @PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.user.username == authentication.principal.username")
    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found by id"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #todo.user.username == authentication.principal.username")
    public Todo save(Todo todo) {
        todoRepository.save(todo);
        return findById(todo.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public List<Todo> findAllByUserUsername(String username, Pageable pageable) {
        return todoRepository.findAllByUserUsernameWithPageable(username, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public List<Todo> findAllByUserUsername(String username) {
        return todoRepository.findAllByUserUsername(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #todo.user.username == authentication.principal.username")
    public Todo update(Todo todo, Long id) {
        Todo todoToUpdate = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found by id #" + id));

        todoToUpdate.setText(todo.getText() != null ? todo.getText() : todoToUpdate.getText());
        todoToUpdate.setTimestamp(todo.getTimestamp() != null ? todo.getTimestamp() : todoToUpdate.getTimestamp());
        todoToUpdate.setDone(todo.isDone() != null ? todo.isDone() : todoToUpdate.isDone());

        todoRepository.update(todo);
        return findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #user.username == authentication.principal.username")
    public void deleteByDone(User user, boolean done) {
        todoRepository.deleteByDone(user, done);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #todo.user.username == authentication.principal.username")
    public void delete(Todo todo) {
        todoRepository.deleteById(todo.getId());
    }
}
