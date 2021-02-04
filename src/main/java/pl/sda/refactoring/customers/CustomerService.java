package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerService {

    private final CustomerDao dao;
    private final MailSender mailSender;

    public CustomerService(CustomerDao dao, MailSender mailSender) {
        this.dao = requireNonNull(dao);
        this.mailSender = requireNonNull(mailSender);
    }

    public boolean registerPerson(RegisterPerson registerPerson) {
        if (personExists(registerPerson.getEmail(), registerPerson.getPesel())) {
            return false;
        }

        var customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setType(Customer.PERSON);
        customer.setCtime(LocalDateTime.now());
        customer.setEmail(registerPerson.getEmail());
        customer.setfName(registerPerson.getFirstName());
        customer.setlName(registerPerson.getLastName());
        customer.setPesel(registerPerson.getPesel());

        String subj;
        String body;
        if (registerPerson.isVerified()) {
            customer.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Hi " + registerPerson.getFirstName() + "</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            customer.setVerf(false);
            subj = "Waiting for verification";
            body = "<b>Hi " + registerPerson.getFirstName() + "</b><br/>" +
                "We registered you in our service. Please wait for verification!";
        }
        dao.save(customer);
        mailSender.send(registerPerson.getEmail(), subj, body);
        return true;
    }

    private boolean personExists(Email email, Pesel pesel) {
        return dao.emailExists(email) || dao.peselExists(pesel);
    }

    public boolean registerCompany(Email email, Name name, String vat, boolean verified) {
        var result = false;
        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        if (!companyExists(email, vat) && isCompanyDataNotNull(email, name, vat)) {
            customer.setEmail(email);
            customer.setCompName(name);
            if (matchesVat(vat)) {
                customer.setCompVat(vat);
            }
            if (isValidCompany(customer)) {
                result = true;
            }
        }

        if (result) {
            customer.setCtime(LocalDateTime.now());
            String subj;
            String body;
            if (verified) {
                customer.markVerified();
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

    private boolean isCompanyDataNotNull(Email email, Name name, String vat) {
        return email != null && name != null && vat != null;
    }

    private boolean companyExists(Email email, String vat) {
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
