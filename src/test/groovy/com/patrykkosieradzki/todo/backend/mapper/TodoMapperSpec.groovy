package com.patrykkosieradzki.todo.backend.mapper

import com.patrykkosieradzki.todo.backend.entity.Todo
import spock.lang.Specification

import java.time.LocalDateTime

class TodoMapperSpec extends Specification {

    def todoMapper = new TodoMapperImpl()

    def "todo is converted to dto properly"() {
        given: 'A todo is created'
        def todo = new Todo("text", true, LocalDateTime.now(), 1)
        todo.id = 1

        and: 'We convert it to dto'
        def todoDto = todoMapper.toDto(todo)

        expect: 'All params match'
        todo.id == todoDto.id
        todo.text == todoDto.text
        todo.done == todoDto.done
        todo.timestamp == todoDto.timestamp
        todo.userId == todoDto.userId
    }

    def "todos are converted to dtos properly"() {
        given: 'todos are created'
        def todoList = List.of(
                new Todo("text", true, LocalDateTime.now(), 1),
                new Todo("text2", false, LocalDateTime.now().plusDays(1), 2)
        )

        todoList[0].userId = 1
        todoList[1].userId = 2

        and: 'We convert them to dto list'
        def todoDtoList = todoMapper.toDto(todoList)

        expect: 'All params match'
        for (int i = 0; i < todoList.size(); i++) {
            def todo = todoList[i]
            def todoDto = todoDtoList[i]

            todo.id == todoDto.id
            todo.text == todoDto.text
            todo.done == todoDto.done
            todo.timestamp == todoDto.timestamp
            todo.userId == todoDto.userId
        }
    }
}