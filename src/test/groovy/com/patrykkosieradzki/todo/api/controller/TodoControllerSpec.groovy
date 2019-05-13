package com.patrykkosieradzki.todo.api.controller

import com.patrykkosieradzki.todo.backend.mapper.TodoMapper
import com.patrykkosieradzki.todo.backend.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [TodoController])
class TodoControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    TodoService todoService

    @Autowired
    TodoMapper todoMapper

    def "get all todos test"() {
        given:
        def results = mvc.perform(get("/api/todos").contentType(MediaType.APPLICATION_JSON))

        expect:
        results.andExpect(status().isOk())
    }

    def "get todo by id"() {
        given:
        def results = mvc.perform(get("/api/todos/1").contentType(MediaType.APPLICATION_JSON))

        expect:
        results.andExpect(status().isOk())
    }

    def "get todo by wrong id"() {
        given:
        def results = mvc.perform(get("/api/todos/0").contentType(MediaType.APPLICATION_JSON))

        expect:
        results.andExpect(content().string(containsString("null")))
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        TodoService todoService() {
            return detachedMockFactory.Stub(TodoService)
        }

        @Bean
        TodoMapper todoMapper() {
            return detachedMockFactory.Stub(TodoMapper)
        }
    }

}