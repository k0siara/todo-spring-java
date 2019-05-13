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
        activatrionToken.value = "qwertyuiop"
        activationToken.expiresAt = LocalDateTime.now().plusDays(7)
        activationTokenRepository.save(activationToken)

        user = new User("John", "Doe", "john_doe", "john.doe@example.com", "password")
        user.activationToken = activationToken
    }

    boolean equals(User u1, User u2) {
        u1.id == u2.id
        u1.firstName == u2.firstName
        u1.lastName == u2.lastName
        u1.username == u2.username
        u1.password == u2.password
        u1.email == u2.email
//        u1.activationToken == u2.activationToken
    }

    def "find user by existing id"() {
        when: "We save user to repository"
        userRepository.save(user)

        and: "We try to find it by id"
        def userFromRepo = userRepository.findById(user.id)

        then: "User is present"
        userFromRepo.isPresent()
        equals(userFromRepo.get(), user)
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
        equals(userFromRepo.get(), user)
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
        equals(userFromRepo.get(), user)
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
        equals(userFromRepo.get(), user)
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
        equals(userFromRepo.get(), user)
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
