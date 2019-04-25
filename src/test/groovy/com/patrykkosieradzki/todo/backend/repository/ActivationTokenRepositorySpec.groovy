package com.patrykkosieradzki.todo.backend.repository

import com.patrykkosieradzki.todo.backend.entity.ActivationToken
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDateTime

@MybatisTest
class ActivationTokenRepositorySpec extends Specification {

    @Autowired
    private ActivationTokenRepository activationTokenRepository;

    private ActivationToken activationToken;

    void setup() {
        activationToken = new ActivationToken()
        activationToken.value = "qwertyuiop"
        activationToken.expiresAt = LocalDateTime.now().plusDays(7)
    }



}
