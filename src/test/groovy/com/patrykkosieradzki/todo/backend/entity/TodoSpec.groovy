package com.patrykkosieradzki.todo.backend.entity

import spock.lang.Specification

import java.time.LocalDateTime


class TodoSpec extends Specification {

    def "todo test"() {
        given:
        def now = LocalDateTime.now()
        def todo = new Todo("text", true, now, 1)

        expect:
        todo.text == "text"
        todo.done
        todo.timestamp == now
        todo.userId == 1
    }

}