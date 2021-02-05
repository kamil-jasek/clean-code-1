package pl.sda.refactoring.application.mail;

public interface MailSender {

    void send(String recipient, String subject, String body);
}
