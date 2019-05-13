package com.patrykkosieradzki.todo.backend.entity

import spock.lang.Specification

import java.time.LocalDateTime


class AbstractEntitySpec extends Specification {


    def "abstract entity test"() {
        given:
        def now = LocalDateTime.now()

        def abstractEntity = new User()
        abstractEntity.id = 1
        abstractEntity.createdAt = now
        abstractEntity.updatedAt = now

        expect:
        abstractEntity.id == 1
        abstractEntity.createdAt == now
        abstractEntity.updatedAt == now
    }

}