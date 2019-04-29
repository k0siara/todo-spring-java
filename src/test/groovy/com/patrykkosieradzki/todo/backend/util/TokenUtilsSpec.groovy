package com.patrykkosieradzki.todo.backend.util

import spock.lang.Specification
import spock.lang.Unroll

class TokenUtilsSpec extends Specification {

    @Unroll("token with #n characters is properly generated")
    def "should generate random token"() {
        given:
        def token = TokenUtils.getRandomToken(n)

        expect:
        token.length() == n

        where:
        n << [2, 4, 5, 10]
    }

    @Unroll("two tokens with #n characters should be different")
    def "should return different tokens"() {
        given:
        def token = TokenUtils.getRandomToken(n)
        def token2 = TokenUtils.getRandomToken(n)

        expect:
        token != token2

        where:
        n << [2, 4, 5, 10]
    }
}
