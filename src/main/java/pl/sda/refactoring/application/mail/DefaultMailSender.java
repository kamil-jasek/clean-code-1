package pl.sda.refactoring.application.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class DefaultMailSender implements MailSender {

    @Override
    public void send(String recipient, String subject, String body) {
        trySend(recipient, subject, body, createSession(prepareProperties()));
    }

    private void trySend(String recipient, String subject, String body, Session session) {
        try {
            final var message = prepareMimeMessage(recipient, subject, session);
            message.setContent(prepareMultipart(body));
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private MimeMultipart prepareMultipart(String body) throws MessagingException {
        final var mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(body, "text/html");
        final var multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        return multipart;
    }

    private MimeMessage prepareMimeMessage(String recipient, String subject, Session session) throws MessagingException {
        final var message = new MimeMessage(session);
        message.setFrom(new InternetAddress("no-reply@company.com"));
        message.setRecipients(RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        return message;
    }

    private Session createSession(Properties prop) {
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin");
            }
        });
    }

    private Properties prepareProperties() {
        final var prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", System.getenv().get("MAIL_SMTP_HOST"));
        prop.put("mail.smtp.port", System.getenv().get("MAIL_SMTP_PORT"));
        prop.put("mail.smtp.ssl.trust", System.getenv().get("MAIL_SMTP_SSL_TRUST"));
        return prop;
    }
}
