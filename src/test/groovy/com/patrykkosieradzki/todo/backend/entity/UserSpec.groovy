package com.patrykkosieradzki.todo.backend.entity

import spock.lang.Specification

import java.time.LocalDateTime


class UserSpec extends Specification {

    def "user test"() {
        given:
        def now = LocalDateTime.now()
        def activationToken = Stub(ActivationToken)
        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password",
                true, true, true, true, now, now, activationToken)

        expect:
        user.firstName == "John"
        user.lastName == "Doe"
        user.username == "jdoe"
        user.email == "jdoe@example.com"
        user.password == "password"
        user.expired
        user.locked
        user.credentialsExpired
        user.enabled
        user.createdAt == now
        user.updatedAt == now
        user.activationToken == activationToken
    }



}