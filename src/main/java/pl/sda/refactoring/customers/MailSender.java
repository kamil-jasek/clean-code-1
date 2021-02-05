package pl.sda.refactoring.customers;

public interface MailSender {

    void send(String recipient, String subject, String body);
}
