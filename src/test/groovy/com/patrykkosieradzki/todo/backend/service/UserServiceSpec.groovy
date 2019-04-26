package com.patrykkosieradzki.todo.backend.service

import com.patrykkosieradzki.todo.backend.entity.User
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository
import com.patrykkosieradzki.todo.backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceSpec extends Specification {

    private UserRepository userRepository = Mock(UserRepository)
    private ActivationTokenRepository activationTokenRepository = Mock(ActivationTokenRepository)
    private PasswordEncoder passwordEncoder = Stub(PasswordEncoder)

    private UserService userService =
            new UserService(userRepository, activationTokenRepository, passwordEncoder)

    def "find all should call user repository"() {
        when: "We call findAll()"
        userService.findAll()

        then: "User repository is used to retrieve all users from database"
        1 * userRepository.findAll()

        and: "Nothing else happens"
        0 * _._
    }

    def "find by id should call user repository"() {
        when: "We call findById()"
        userService.findById(1)

        then: "User repository is used to retrieve all users from database"
        1 * userRepository.findById(1)
    }

    def "save should call repository"() {
        given: "A user"
        User user = Stub(User)

        when: "We use service to save it"
        userService.save(user)

        then:"User repository is used to save user to database"
        1 * userRepository.save(_ as User)
        0 * _._
    }

    def "update should call user repository"() {
        given: "A user"
        User user = Stub(User)

        when: "We use service to update it"
        userService.update(user)

        then:"User repository is used to update user in database"
        1 * userRepository.update(_ as User)
        0 * _._
    }

    def "register"() {
        given: "A user"
        User user = Stub(User)
        user.password = "password"

        when:
        userService.register(user)

        then: "Password is encoded"
        passwordEncoder.matches(user.password, "password")
    }

}
