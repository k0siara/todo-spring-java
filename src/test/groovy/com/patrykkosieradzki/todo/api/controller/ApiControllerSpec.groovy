package com.patrykkosieradzki.todo.api.controller


import com.patrykkosieradzki.todo.app.security.SecurityConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = [ApiController])
class ApiControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc

    def "home test api info"() {
        when:
        def results = mvc.perform(get("/api").contentType(MediaType.APPLICATION_JSON))

        then:
        results.andExpect(status().isOk())
    }

}