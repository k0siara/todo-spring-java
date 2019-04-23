package com.patrykkosieradzki.todo.backend.repository

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import spock.lang.Title

@MybatisTest
@Title("User repository tests")
class UserRepositorySpec extends Specification {

    @Autowired
    private UserRepository userRepository

    def "Find user by id"() {
        when: 'We find a user by id'
        def user = userRepository.findById(1L)


        then: 'User is not null'
        user.isPresent()
    }

}
