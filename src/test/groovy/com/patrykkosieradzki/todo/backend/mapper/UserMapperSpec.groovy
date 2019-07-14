package com.patrykkosieradzki.todo.backend.mapper

import com.patrykkosieradzki.todo.backend.dto.UserDTO
import com.patrykkosieradzki.todo.backend.entity.User
import com.patrykkosieradzki.todo.backend.service.UserService
import spock.lang.Specification

class UserMapperSpec extends Specification {

    def userService = Mock(UserService)
    def userMapper = new UserMapperImpl()

    void setup() {
        userMapper.userService = userService
    }

    def "user is converted to dto properly"() {
        given: 'A user is given'
        def user = Stub(User.class)

        and:
        def userDto = userMapper.toUserDTO(user)

        expect:
        user.firstName == userDto.firstName
        user.lastName == userDto.lastName
        user.username == userDto.username
        user.email == userDto.email
    }

    def "a list of users is converted to user dtos properly"() {
        given: 'A list of users is given'
        def users = List.of(
                Stub(User.class),
                Stub(User.class),
                Stub(User.class)
        )

        and:
        def userDtos = userMapper.toUserDTOs(users)

        expect:
        for (int i = 0; i < users.size(); i++) {
            def user = users[i]
            def userDto = userDtos[i]

            user.firstName == userDto.firstName
            user.lastName == userDto.lastName
            user.username == userDto.username
            user.email == userDto.email
        }
    }

    def "user dto is converted to user properly"() {
        given: 'A user dto is given'
        def userDto = new UserDTO("john", "doe", "jdoe", "jdoe@example.com")
        userDto.id = 1

        def usr = new User()
        usr.id = 1

        and:
        userService.findById(1) >> usr

        when:
        def user = userMapper.toEntity(userDto)

        then:
        user.id == userDto.id
    }

}
