package pl.sda.refactoring.customers;

final class EventPublisherConfig {

    EventPublisher configure(MailSender mailSender, ExternalSystem externalSystem) {
        final var publisher = new EventPublisher();
        publisher.register(new SendMailAboutRegisteredPersonObserver(mailSender));
        publisher.register(new SendMailAboutRegisteredCompanyObserver(mailSender));
        publisher.register(new NotifyExternalSystemObserver(externalSystem));
        return publisher;
    }
}
