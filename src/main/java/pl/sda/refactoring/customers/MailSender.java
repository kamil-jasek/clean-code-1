package pl.sda.refactoring.customers;

public interface MailSender {

    void send(Email recipient, String subject, String body);
}
