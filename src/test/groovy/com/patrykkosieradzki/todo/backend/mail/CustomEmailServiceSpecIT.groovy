package com.patrykkosieradzki.todo.backend.mail

import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetup
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class CustomEmailServiceSpecIT extends Specification {

    @Autowired
    private CustomEmailService customEmailService

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

        then: "Sender is the same"
        messages[0].from[0].toString() == email.from

        then: "Receiver is the same"
        messages[0].allRecipients[0].toString() == email.to

        then: "Subject is the same"
        messages[0].subject == email.subject

        then: "Content is the same"
        messages[0].getContent().toString().contains(email.text)

        then: "Exception is not thrown"
        noExceptionThrown()
    }
}
