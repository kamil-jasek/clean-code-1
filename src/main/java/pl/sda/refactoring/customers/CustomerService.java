package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class CustomerService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    private final CustomerDao dao;
    private final MailSender mailSender;

    public CustomerService(CustomerDao dao, MailSender mailSender) {
        this.dao = requireNonNull(dao);
        this.mailSender = requireNonNull(mailSender);
    }

    public boolean registerPerson(String email, String firstName, String lastName, String pesel, boolean verified) {
        if (personExists(email, pesel) || !isPersonDataNotNull(email, firstName, lastName, pesel)) {
            return false;
        }

        var result = false;
        var customer = new Customer();
        customer.setType(Customer.PERSON);

        if (matchesEmail(email)) {
            customer.setEmail(email);
        }
        if (matchesName(firstName)) {
            customer.setfName(firstName);
        }
        if (matchesName(lastName)) {
            customer.setlName(lastName);
        }
        if (matchesPesel(pesel)) {
            customer.setPesel(pesel);
        }
        if (isValidPerson(customer)) {
            result = true;
        }

        if (result) {
            customer.setCtime(LocalDateTime.now());
            String subj;
            String body;
            if (verified) {
                customer.markVerified();
                subj = "Your are now verified customer!";
                body = "<b>Hi " + firstName + "</b><br/>" +
                    "Thank you for registering in our service. Now you are verified customer!";
            } else {
                customer.setVerf(false);
                subj = "Waiting for verification";
                body = "<b>Hi " + firstName + "</b><br/>" +
                    "We registered you in our service. Please wait for verification!";
            }
            customer.setId(UUID.randomUUID());
            dao.save(customer);
            mailSender.send(email, subj, body);
        }

        return result;
    }

    private boolean matchesPesel(String pesel) {
        return pesel.matches("\\d{11}");
    }

    private boolean matchesEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean matchesName(String name) {
        return name.matches("[\\p{L}\\s\\.]{2,100}");
    }

    private boolean isPersonDataNotNull(String email, String firstName, String lastName, String pesel) {
        return email != null && firstName != null && lastName != null && pesel != null;
    }

    private boolean personExists(String email, String pesel) {
        return dao.emailExists(email) || dao.peselExists(pesel);
    }

    public boolean registerCompany(String email, String name, String vat, boolean verified) {
        var result = false;
        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        if (!companyExists(email, vat)) {
            if (isCompanyDataNotNull(email, name, vat)) {
                if (matchesEmail(email)) {
                    customer.setEmail(email);
                }
                if (matchesName(name)) {
                    customer.setCompName(name);
                }
                if (matchesVat(vat)) {
                    customer.setCompVat(vat);
                }

                if (isValidCompany(customer)) {
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
            customer.setId(UUID.randomUUID());
            dao.save(customer);
            // send email to customer
            mailSender.send(email, subj, body);
        }

        return result;
    }

    private boolean isValidCompany(Customer customer) {
        return customer.getEmail() != null && customer.getCompName() != null && customer.getCompVat() != null;
    }

    private boolean matchesVat(String vat) {
        return vat.matches("\\d{10}");
    }

    private boolean isCompanyDataNotNull(String email, String name, String vat) {
        return email != null && name != null && vat != null;
    }

    private boolean companyExists(String email, String vat) {
        return dao.emailExists(email) || dao.vatExists(vat);
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

    private boolean isValid(boolean flag) {
        return flag == true;
    }
}
