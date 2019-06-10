package com.patrykkosieradzki.todo.backend.service

import com.patrykkosieradzki.todo.backend.entity.Todo
import com.patrykkosieradzki.todo.backend.entity.User
import com.patrykkosieradzki.todo.backend.repository.TodoRepository
import spock.lang.Specification

import java.time.LocalDateTime


class TodoServiceSpec extends Specification {

    def todoRepository = Mock(TodoRepository)
    def todoService = new TodoService(todoRepository)

    def "find all calls repository for all todos"() {
        when:
        todoService.findAll()

        then:
        1 * todoRepository.findAll()
    }

    def "find by id returns one todo"() {
        given:
        def now = LocalDateTime.now()
        def todo = new Todo("text", false, now, 1)

        and:
        todoRepository.findById(1) >> Optional.of(todo)

        expect:
        todoService.findById(1) == todo
    }

    def "save uses repository to save todo in database"() {
        given:
        def now = LocalDateTime.now()
        def todo = new Todo("text", false, now, 1)
        todo.id = 1

        and:
        todoRepository.findById(1) >> Optional.of(todo)

        when:
        def newTodo = todoService.save(todo)

        then:
        1 * todoRepository.save(todo)
        todo == newTodo
    }

    def "find all by user id returns list with user's todos"() {
        when:
        todoService.findAllByUserUsername(1)

        then:
        1 * todoRepository.findAllByUserUsernameWithPageable(1)
    }

    def "update uses repository to update todo in database"() {
        given:
        def now = LocalDateTime.now()
        def todo = new Todo("text", false, now, 1)
        todo.id = 1

        and:
        todoRepository.findById(1) >> Optional.of(todo)

        when:
        def newTodo = todoService.update(todo)

        then:
        1 * todoRepository.update(todo)
        todo == newTodo
    }

    def "delete by done test"() {
        given:
        def user = Stub(User)

        when:
        todoService.deleteByDone(user, true)

        then:
        1 * todoRepository.deleteByDone(user, true)
    }

    def "delete by id test"() {
        when:
        todoService.delete(1)

        then:
        todoRepository.deleteById(1)
    }

}