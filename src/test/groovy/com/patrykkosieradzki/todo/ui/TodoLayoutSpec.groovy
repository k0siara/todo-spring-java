package com.patrykkosieradzki.todo.ui

import com.patrykkosieradzki.todo.backend.entity.Todo
import com.vaadin.flow.data.value.ValueChangeMode
import spock.lang.Specification

import java.time.LocalDateTime


class TodoLayoutSpec extends Specification {

    def "todo layout test"() {
        given:
        def now = LocalDateTime.now()
        def todo = new Todo("text", false, now, 1)
        def todoListener = Stub(TodoListener)

        and:
        def todoLayout = new TodoLayout(todo, todoListener)

        expect:
        todoLayout.text.valueChangeMode == ValueChangeMode.ON_BLUR
        todoLayout.text.readOnly

        todoLayout.text.value == todo.text

        todoLayout.todo == todo
        todoLayout.todoListener == todoListener
    }


}