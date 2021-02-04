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

    public boolean registerCompany(RegisterCompany registerCompany) {
        if (companyExists(registerCompany.getEmail(), registerCompany.getVat())) {
            return false;
        }

        var customer = new Customer();
        customer.setType(Customer.COMPANY);
        customer.setId(UUID.randomUUID());
        customer.setEmail(registerCompany.getEmail());
        customer.setCompName(registerCompany.getName());
        customer.setCompVat(registerCompany.getVat());
        customer.setCtime(LocalDateTime.now());

        String subj;
        String body;
        if (registerCompany.isVerified()) {
            customer.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Your company: " + registerCompany.getName() + " is ready to make na order.</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            customer.setVerf(false);
            subj = "Waiting for verification";
            body = "<b>Hello</b><br/>" +
                "We registered your company: " + registerCompany.getName() + " in our service. Please wait for verification!";
        }
        dao.save(customer);
        mailSender.send(registerCompany.getEmail(), subj, body);

        return true;
    }

    private boolean isValidCompany(Customer customer) {
        return customer.getEmail() != null && customer.getCompName() != null && customer.getCompVat() != null;
    }

    private boolean isCompanyDataNotNull(Email email, Name name, Vat vat) {
        return email != null && name != null && vat != null;
    }

    private boolean companyExists(Email email, Vat vat) {
        return dao.emailExists(email) || dao.vatExists(vat);
    }

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

}
