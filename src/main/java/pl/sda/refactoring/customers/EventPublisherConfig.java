package pl.sda.refactoring.customers;

import pl.sda.refactoring.application.events.EventPublisher;
import pl.sda.refactoring.application.mail.MailSender;

final class EventPublisherConfig {

    public void configure(EventPublisher publisher, MailSender mailSender, ExternalSystem externalSystem) {
        publisher.register(new SendMailAboutRegisteredPersonObserver(mailSender));
        publisher.register(new SendMailAboutRegisteredCompanyObserver(mailSender));
        publisher.register(new NotifyExternalSystemObserver(externalSystem));
    }
}
