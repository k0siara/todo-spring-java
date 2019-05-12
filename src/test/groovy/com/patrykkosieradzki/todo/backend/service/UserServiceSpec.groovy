package com.patrykkosieradzki.todo.backend.service


import com.patrykkosieradzki.todo.backend.entity.User
import com.patrykkosieradzki.todo.backend.repository.ActivationTokenRepository
import com.patrykkosieradzki.todo.backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceSpec extends Specification {

    private UserRepository userRepository = Mock(UserRepository.class)
    private ActivationTokenRepository activationTokenRepository = Mock(ActivationTokenRepository.class)

    private PasswordEncoder passwordEncoder = Stub()

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
        given:
        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password")
        user.id = 1

        and:
        userRepository.findById(1) >> Optional.of(user)

        expect:
        userService.findById(1) == user

    }

    def "find by id should throw exception when user is not found"() {
        given:
        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password")
        user.id = 1

        and:
        userRepository.findById(1) >> Optional.ofNullable(null)

        when:
        userService.findById(1)

        then:
        RuntimeException exception = thrown()
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

//    def "register test "() {
//        given: "A user"
//        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password")
//
//        def now = LocalDateTime.now()
//        def activationToken = new ActivationToken("token", now, now, now)
//        activationToken.id = 1
//
//        and:
//        userService.createActivationToken() >> activationToken
//        activationTokenRepository.findById(1) >> Optional.of(activationToken)
//
//        when:
//        userService.register(user)
//
//        then:
//        passwordEncoder.matches(user.password, "password")
//    }

    def "save user test"() {
        given:
        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password")

        when:
        userService.save(user)

        then:
        1 * userRepository.save(user)
    }

    def "update user test"() {
        given:
        def user = new User("John", "Doe", "jdoe", "jdoe@example.com", "password")

        when:
        userService.update(user)

        then:
        1 * userRepository.update(user)
    }

}
