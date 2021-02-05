package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

final class SendMailAboutRegisteredPersonObserver implements EventObserver<RegisteredPersonEvent> {

    private final MailSender mailSender;

    SendMailAboutRegisteredPersonObserver(MailSender mailSender) {
        this.mailSender = requireNonNull(mailSender);
    }

    @Override
    public boolean supports(Event event) {
        return event instanceof RegisteredPersonEvent;
    }

    @Override
    public void handle(RegisteredPersonEvent event) {
        if (event.isVerified()) {
            sendVerifiedMail(event);
        } else {
            sendNotVerifiedMail(event);
        }
    }

    private void sendVerifiedMail(RegisteredPersonEvent event) {
        final var subject = "Your are now verified customer!";
        final var body = "<b>Hi " + event.getFirstName() + "</b><br/>" +
            "Thank you for registering in our service. Now you are verified customer!";
        mailSender.send(event.getEmail(), subject, body);
    }

    private void sendNotVerifiedMail(RegisteredPersonEvent event) {
        final var subject = "Waiting for verification";
        final var body = "<b>Hi " + event.getFirstName() + "</b><br/>" +
            "We registered you in our service. Please wait for verification!";
        mailSender.send(event.getEmail(), subject, body);
    }
}
