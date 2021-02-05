package pl.sda.refactoring.customers;

import pl.sda.refactoring.application.events.EventPublisher;

final class TestEventPublisherConfig {

    private final TestMailSender mailSender = new TestMailSender();
    private final TestExternalSystem externalSystem = new TestExternalSystem();

    EventPublisher empty() {
        return new EventPublisher();
    }

    EventPublisher configured() {
        final var publisher = new EventPublisher();
        final var config = new EventPublisherConfig();
        config.configure(publisher, mailSender, externalSystem);
        return publisher;
    }

    TestMailSender mailSender() {
        return mailSender;
    }

    TestExternalSystem externalSystem() {
        return externalSystem;
    }

    void resetMocks() {
        this.mailSender.reset();
        this.externalSystem.reset();
    }
}
