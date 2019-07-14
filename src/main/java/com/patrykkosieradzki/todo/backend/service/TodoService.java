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

    public List<Todo> findAll(Pageable pageable) {
        return todoRepository.findAll();
    }

    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found by id"));
    }

    public Todo save(Todo todo) {
        todoRepository.save(todo);
        return findById(todo.getId());
    }

    public List<Todo> findAllByUserUsername(String username, Pageable pageable) {
        return todoRepository.findAllByUserUsernameWithPageable(username, pageable);
    }

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

    public void delete(Todo todo) {
        todoRepository.deleteById(todo.getId());
    }
}
