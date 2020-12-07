package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CustomerService {

    private CustomerDao dao;

    /**
     * Register new person type customer
     * @param email
     * @param fName
     * @param lName
     * @param pesel
     * @param verified
     * @return
     */
    public boolean registerPerson(String email, String fName, String lName, String pesel, boolean verified) {
        var result = false;
        var customer = new Customer();
        customer.setType(Customer.PERSON);
        var isInDb = dao.emailExists(email) || dao.peselExists(pesel);
        if (!isInDb) {
            if (email != null && fName != null && lName != null && pesel != null) {
                var emailP = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                var emailM = emailP.matcher(email);
                if (emailM.matches()) {
                    customer.setEmail(email);
                }
                if (fName.length() > 0 && fName.matches("/\\p{L}{2,100}/")) {
                    customer.setfName(fName);
                }
                if (lName.length() > 0 && lName.matches("/\\p{L}{2,100}/")) {
                    customer.setlName(lName);
                }
                if (pesel.length() == 11 && pesel.matches("/\\d{11}/")) {
                    customer.setPesel(pesel);
                }

                if (isValidPerson(customer)) {
                    result = true;
                }
            }
        }

        if (result == true) {
            customer.setCtime(LocalDateTime.now());
            String subj;
            String body;
            if (verified) {
                customer.setVerf(verified);
                customer.setVerfTime(LocalDateTime.now());
                customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
                subj = "Your are now verified customer!";
                body = "<b>Hi " + fName + "</b><br/>" +
                    "Thank you for registering in our service. Now you are verified customer!";
            } else {
                customer.setVerf(false);
                subj = "Waiting for verification";
                body = "<b>Hi " + fName + "</b><br/>" +
                    "We registered you in our service. Please wait for verification!";
            }
            genCustomerId(customer);
            dao.save(customer);
            // send email to customer
            sendEmail(email, subj, body);
        }

        return result;
    }

    /**
     * Register new company type customer
     * @param email
     * @param name
     * @param vat
     * @param verified
     * @return
     */
    public boolean registerCompany(String email, String name, String vat, boolean verified) {
        var result = false;
        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        var isInDb = dao.emailExists(email) || dao.vatExists(vat);
        if (!isInDb) {
            if (email != null && name != null && vat != null) {
                var emailP = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                var emailM = emailP.matcher(email);
                if (emailM.matches()) {
                    customer.setEmail(email);
                }
                if (name.length() > 0 && name.matches("/\\p{L}{2,100}/")) {
                    customer.setCompName(name);
                }
                if (vat.length() == 10 && vat.matches("/\\d{10}/")) {
                    customer.setCompVat(vat);
                }

                if (isValidPerson(customer)) {
                    result = true;
                }
            }
        }

        if (result == true) {
            customer.setCtime(LocalDateTime.now());
            String subj;
            String body;
            if (verified) {
                customer.setVerf(verified);
                customer.setVerfTime(LocalDateTime.now());
                customer.setVerifBy(CustomerVerifier.AUTO_EMAIL);
                subj = "Your are now verified customer!";
                body = "<b>Your company: " + name + " is ready to make na order.</b><br/>" +
                    "Thank you for registering in our service. Now you are verified customer!";
            } else {
                customer.setVerf(false);
                subj = "Waiting for verification";
                body = "<b>Hello</b><br/>" +
                    "We registered your company: " + name + " in our service. Please wait for verification!";
            }
            genCustomerId(customer);
            dao.save(customer);
            // send email to customer
            sendEmail(email, subj, body);
        }

        return result;
    }

    /**
     * Set new address for customer
     * @param cid
     * @param str
     * @param zipcode
     * @param city
     * @param country
     * @return
     */
    public boolean updateAddress(UUID cid, String str, String zipcode, String city, String country) {
        var result = false;
        var customer = dao.findById(cid);
        if (customer.isPresent()) {
           var object = customer.get();
           object.setAddrStreet(str);
           object.setAddrZipCode(zipcode);
           object.setAddrCity(city);
           object.setAddrCountryCode(country);
           dao.save(object);
           result = true;
        }
        return result;
    }

    private boolean isValidPerson(Customer customer) {
        return customer.getEmail() != null && customer.getfName() != null && customer.getlName() != null && customer.getPesel() != null;
    }

    private void genCustomerId(Customer customer) {
        customer.setId(UUID.randomUUID());
    }

    private boolean isValid(boolean flag) {
        return flag == true;
    }

    private boolean sendEmail(String address, String subj, String msg) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", System.getenv().get("MAIL_SMTP_HOST"));
        prop.put("mail.smtp.port", System.getenv().get("MAIL_SMTP_PORT"));
        prop.put("mail.smtp.ssl.trust", System.getenv().get("MAIL_SMTP_SSL_TRUST"));

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@company.com"));
            message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(subj);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void setDao(CustomerDao dao) {
        this.dao = dao;
    }
}
