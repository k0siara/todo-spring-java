package com.patrykkosieradzki.todo.api

import spock.lang.Specification


class ApiInfoSpec extends Specification {

    def "api info test"() {
        given:
        def apiInfo = new ApiInfo("1.0", "url")

        expect:
        apiInfo.version == "1.0"
        apiInfo.documentationUrl == "url"
    }

}