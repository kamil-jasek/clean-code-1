package pl.sda.refactoring.customers;

import pl.sda.refactoring.application.mail.MailSender;
import pl.sda.refactoring.customers.TestMailSender.MailData;

final class TestMailSender extends TestCapture<MailData> implements MailSender {

    static class MailData {
        private final String recipient;
        private final String subject;
        private final String body;

        MailData(String recipient, String subject, String body) {
            this.recipient = recipient;
            this.subject = subject;
            this.body = body;
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

    @Override
    public void send(String recipient, String subject, String body) {
        setCapture(new MailData(recipient, subject, body));
    }
}
