package com.patrykkosieradzki.todo.backend.mail

import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetup
import com.patrykkosieradzki.todo.backend.service.EmailService
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class CustomEmailServiceSpecIT extends Specification {

    @Autowired
    private EmailService customEmailService

    @Rule
    private GreenMailRule greenMailRule =
            new GreenMailRule(new ServerSetup(3025, "localhost", ServerSetup.PROTOCOL_SMTP))

    private Email email

    void setup() {
        email = new Email()
        email.from = "todo.spring.java@outlook.com"
        email.to = "mhi71329@cndps.com"
        email.subject = "subject"
        email.text = "text"
        email.multipart = false
    }

    def "send message"() {
        when: "We send a message"
        customEmailService.sendMessage(email)

        and: "Retrieve it from inbox"
        def messages = greenMailRule.getReceivedMessages()

        then: "Message is present"
        messages.length == 1

        and: "Sender is the same"
        messages[0].from[0].toString() == email.from

        and: "Receiver is the same"
        messages[0].allRecipients[0].toString() == email.to

        and: "Subject is the same"
        messages[0].subject == email.subject

        and: "Content is the same"
        messages[0].getContent().toString().contains(email.text)

        and: "Exception is not thrown"
        noExceptionThrown()
    }
}
