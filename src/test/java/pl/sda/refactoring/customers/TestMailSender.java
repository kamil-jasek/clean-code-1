package pl.sda.refactoring.customers;

final class TestMailSender implements MailSender {

    private String recipient;
    private String subject;
    private String body;

    @Override
    public void send(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    void reset() {
        this.recipient = null;
        this.subject = null;
        this.body = null;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
