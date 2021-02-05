package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.application.events.Event;
import pl.sda.refactoring.application.events.EventObserver;
import pl.sda.refactoring.application.mail.MailSender;
import pl.sda.refactoring.customers.events.RegisteredCompanyEvent;

final class SendMailAboutRegisteredCompanyObserver implements EventObserver<RegisteredCompanyEvent> {

    private final MailSender mailSender;

    public SendMailAboutRegisteredCompanyObserver(MailSender mailSender) {
        this.mailSender = requireNonNull(mailSender);
    }

    @Override
    public boolean supports(Event event) {
        return event instanceof RegisteredCompanyEvent;
    }

    @Override
    public void handle(RegisteredCompanyEvent event) {
        if (event.isVerified()) {
            sendVerifiedMail(event);
        } else {
            sendNotVerifiedMail(event);
        }
    }

    private void sendVerifiedMail(RegisteredCompanyEvent event) {
        final var subject = "Your are now verified customer!";
        final var body = "<b>Your company: " + event.getName() + " is ready to make na order.</b><br/>" +
            "Thank you for registering in our service. Now you are verified customer!";
        mailSender.send(event.getEmail(), subject, body);
    }

    private void sendNotVerifiedMail(RegisteredCompanyEvent event) {
        final var subject = "Waiting for verification";
        final var body = "<b>Hello</b><br/>" +
            "We registered your company: " + event.getName() + " in our service. Please wait for verification!";
        mailSender.send(event.getEmail(), subject, body);
    }
}
