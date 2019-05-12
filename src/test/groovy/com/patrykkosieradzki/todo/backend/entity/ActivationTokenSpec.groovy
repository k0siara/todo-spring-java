package com.patrykkosieradzki.todo.backend.entity

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Period


class ActivationTokenSpec extends Specification {

    def "activation token test"() {
        given:
        def now = LocalDateTime.now()
        def activationToken = new ActivationToken("token", now, now, now)

        expect:
        activationToken.value == "token"
        activationToken.expiresAt == now
        activationToken.createdAt == now
        activationToken.updatedAt == now
    }

    def "when create is called then random token is created"() {
        given:
        def activationToken = ActivationToken.create()

        and:
        def expiresAt = activationToken.expiresAt
        def now = LocalDateTime.now()

        expect:
        activationToken.value.length() == 32
        Period.between(now.toLocalDate(), expiresAt.toLocalDate()).days == 7
    }

}