package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import pl.sda.refactoring.application.events.EventPublisher;
import pl.sda.refactoring.customers.ExternalSystem.RegisteredCustomer;
import pl.sda.refactoring.customers.dto.CustomerVerification;
import pl.sda.refactoring.customers.dto.CustomerVerifier;
import pl.sda.refactoring.customers.events.RegisteredCompanyEvent;
import pl.sda.refactoring.customers.events.RegisteredPersonEvent;

@TestInstance(Lifecycle.PER_CLASS)
class EventPublisherTest {

    private final TestMailSender mailSender = new TestMailSender();
    private final TestExternalSystem externalSystem = new TestExternalSystem();
    private final EventPublisher publisher = new EventPublisher();
    private final EventPublisherConfig config = new EventPublisherConfig();

    @BeforeAll
    void initTests() {
        config.configure(publisher, mailSender, externalSystem);
    }

    @BeforeEach
    void beforeTest() {
        mailSender.reset();
        externalSystem.reset();
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
        final var capture = mailSender.getCapture();
        assertEquals("em@test.com", capture.getRecipient());
        assertEquals("Your are now verified customer!", capture.getSubject());
        assertEquals("<b>Hi Jan</b><br/>Thank you for registering in our service. Now you are verified customer!",
            capture.getBody());
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
        final var capture = mailSender.getCapture();
        assertEquals("em@test.com", capture.getRecipient());
        assertEquals("Waiting for verification", capture.getSubject());
        assertEquals("<b>Hi Jan</b><br/>We registered you in our service. Please wait for verification!",
            capture.getBody());
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
        final var capture = mailSender.getCapture();
        assertEquals("em@test.com", capture.getRecipient());
        assertEquals("Your are now verified customer!", capture.getSubject());
        assertEquals("<b>Your company: test is ready to make na order.</b><br/>"
                + "Thank you for registering in our service. Now you are verified customer!",
            capture.getBody());
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
        final var capture = mailSender.getCapture();
        assertEquals("em@test.com", capture.getRecipient());
        assertEquals("Waiting for verification", capture.getSubject());
        assertEquals("<b>Hello</b><br/>We registered your company: test in our service. Please wait for verification!",
            capture.getBody());
    }

    @Test
    void shouldNotifyExternalSystemAboutRegisteredCompany() {
        // given
        final var customerId = UUID.randomUUID();

        // when
        publisher.publish(new RegisteredCompanyEvent(
            customerId,
            "em@test.com",
            LocalDateTime.now(),
            "test",
            "93839402033",
            null));

        // then
        assertEquals(new RegisteredCustomer(customerId, "em@test.com", "test", "93839402033"), externalSystem.getCapture());
    }

    @Test
    void shouldNotifyExternalSystemAboutRegisteredPerson() {
        // given
        final var customerId = UUID.randomUUID();

        // when
        publisher.publish(new RegisteredPersonEvent(
            customerId,
            "em@test.com",
            LocalDateTime.now(),
            "Jan",
            "Nowak",
            "8304020340430",
            null));

        // then
        assertEquals(new RegisteredCustomer(customerId, "em@test.com", "Jan Nowak", "8304020340430"), externalSystem.getCapture());
    }
}