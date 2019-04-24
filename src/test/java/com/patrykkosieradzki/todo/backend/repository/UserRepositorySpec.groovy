package com.patrykkosieradzki.todo.backend.repository

import com.patrykkosieradzki.todo.backend.entity.ActivationToken
import com.patrykkosieradzki.todo.backend.entity.User
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDateTime

@MybatisTest
class UserRepositorySpec extends Specification {

    @Autowired
    private ActivationTokenRepository activationTokenRepository

    @Autowired
    private UserRepository userRepository

    private ActivationToken activationToken
    private User user

    void setup() {
        activationToken = new ActivationToken()
        activationToken.value = "qwertyuiop"
        activationToken.expiresAt = LocalDateTime.now().plusDays(7)
        activationTokenRepository.save(activationToken)

        user = new User()
        user.firstName = "John"
        user.lastName = "Doe"
        user.username = "john_doe"
        user.email = "john.doe@example.com"
        user.password = "password"
        user.activationTokenId = activationToken.id
    }

    def "find user by existing id"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "We try to find it by id"
        def userFromRepo = userRepository.findById(user.id)

        then: "User is present"
        userFromRepo.isPresent()
        user.id == userFromRepo.get().id
    }

    def "find user by non-existing id"() {
        when: "We try to find user by non existing id"
        def userFromRepo = userRepository.findById(10L)

        then: "User is not present"
        !userFromRepo.isPresent()
    }

    def "find user by existing username"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "We try to find it by username"
        def userFromRepo = userRepository.findByUsername(user.username)

        then: "User is present"
        userFromRepo.isPresent()
        user.username == userFromRepo.get().username
    }

    def "find user by non-existing username"() {
        when: "We try to find user by non existing username"
        def userFromRepo = userRepository.findByUsername("tom_holland")

        then: "User is not present"
        !userFromRepo.isPresent()
    }

    def "find user by existing email"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "We try to find it by email"
        def userFromRepo = userRepository.findByEmail(user.email)

        then: "User is present"
        userFromRepo.present
        user.email == userFromRepo.get().email
    }

    def "find user by non-existing email"() {
        when: "We try to find user by non existing email"
        def userFromRepo = userRepository.findByEmail("tom.holland@example.com")

        then: "User is not present"
        !userFromRepo.present
    }

    def "find user by existing activation token"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "We try to find it by activation token"
        def userFromRepo = userRepository.findByActivationToken(activationToken.value)

        then: "User is present"
        userFromRepo.present
        user.activationTokenId == activationToken.id
    }

    def "find user by non-existing activation token"() {
        when: "We try to find user by non-existing activation token"
        def userFromRepo = userRepository.findByActivationToken("1qaz2wsx3e")

        then: "User is not present"
        !userFromRepo.present
    }

    def "update user"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "Change his first name"
        user.firstName = "Jack"

        and: "Update changes to repository"
        userRepository.update(user)

        and: "Retrieve user from repository"
        def userFromRepo = userRepository.findById(user.id)

        then: "First name is updated"
        userFromRepo.present
        user.firstName == userFromRepo.get().firstName
    }

    def "exists by taken field name"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "Check if username is taken"
        def exists = userRepository.existsByFieldName("username", user.username)

        then: "Username is taken"
        exists
    }

    def "exists by not taken field name"() {
        when: "We don't save any users to repository"

        and: "Check if username is taken"
        def exists = userRepository.existsByFieldName("username", user.username)

        then: "Username is not taken"
        !exists
    }

}
