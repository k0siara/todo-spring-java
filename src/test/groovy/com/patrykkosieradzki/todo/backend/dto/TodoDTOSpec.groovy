package com.patrykkosieradzki.todo.backend.dto

import spock.lang.Specification

import java.time.LocalDateTime


class TodoDTOSpec extends Specification {

    def "todo dto test"() {
        given:
        def now = LocalDateTime.now()
        def todoDto = new TodoDTO("text", true, now, 1)

        expect:
        todoDto.text == "text"
        todoDto.done
        todoDto.timestamp == now
        todoDto.userId == 1
    }

}