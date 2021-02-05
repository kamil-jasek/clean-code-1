package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventPublisherTest {

    private final EventPublisherConfig config = new EventPublisherConfig();
    private final TestMailSender mailSender = new TestMailSender();
    private final ExternalSystem externalSystem = mock(ExternalSystem.class);
    private final EventPublisher publisher = config.configure(mailSender, externalSystem);

    @BeforeEach
    void beforeTest() {
        mailSender.reset();
    }

    @Test
    void shouldSendVerifiedMailAboutRegisteredPerson() {
        // when
        publisher.publish(new RegisteredPersonEvent(
            UUID.randomUUID(),
            "em@test.com",
            LocalDateTime.now(),
            "Jan",
            "Kowalski",
            "93939939222",
            new CustomerVerification(LocalDateTime.now(), CustomerVerifier.AUTO_EMAIL)
        ));

        // then
        assertEquals("em@test.com", mailSender.getRecipient());
        assertEquals("Your are now verified customer!", mailSender.getSubject());
        assertEquals("<b>Hi Jan</b><br/>Thank you for registering in our service. Now you are verified customer!",
            mailSender.getBody());
    }
}