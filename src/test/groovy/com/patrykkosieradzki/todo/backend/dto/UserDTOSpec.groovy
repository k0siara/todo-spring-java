package com.patrykkosieradzki.todo.backend.dto

import spock.lang.Specification


class UserDTOSpec extends Specification {

    def "user dto test"() {
        given:
        def userDto = new UserDTO("john", "doe", "jdoe", "jdoe@example.com")

        expect:
        userDto.firstName == "john"
        userDto.lastName == "doe"
        userDto.username == "jdoe"
        userDto.email == "jdoe@example.com"
    }


}