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

    @Test
    void shouldSendNotVerifiedMailAboutRegisteredPerson() {
        // when
        publisher.publish(new RegisteredPersonEvent(
            UUID.randomUUID(),
            "em@test.com",
            LocalDateTime.now(),
            "Jan",
            "Kowalski",
            "93939939222",
            null));

        // then
        assertEquals("em@test.com", mailSender.getRecipient());
        assertEquals("Waiting for verification", mailSender.getSubject());
        assertEquals("<b>Hi Jan</b><br/>We registered you in our service. Please wait for verification!",
            mailSender.getBody());
    }

    @Test
    void shouldSendVerifiedMailAboutRegisteredCompany() {
        // when
        publisher.publish(new RegisteredCompanyEvent(
            UUID.randomUUID(),
            "em@test.com",
            LocalDateTime.now(),
            "test",
            "93839402033",
            new CustomerVerification(LocalDateTime.now(), CustomerVerifier.AUTO_EMAIL)));

        // then
        assertEquals("em@test.com", mailSender.getRecipient());
        assertEquals("Your are now verified customer!", mailSender.getSubject());
        assertEquals("<b>Your company: test is ready to make na order.</b><br/>"
                + "Thank you for registering in our service. Now you are verified customer!",
            mailSender.getBody());
    }

    @Test
    void shouldSendNotVerifiedMailAboutRegisteredCompany() {
        // when
        publisher.publish(new RegisteredCompanyEvent(
            UUID.randomUUID(),
            "em@test.com",
            LocalDateTime.now(),
            "test",
            "93839402033",
            null));

        // then
        assertEquals("em@test.com", mailSender.getRecipient());
        assertEquals("Waiting for verification", mailSender.getSubject());
        assertEquals("<b>Hello</b><br/>We registered your company: test in our service. Please wait for verification!",
            mailSender.getBody());
    }
}