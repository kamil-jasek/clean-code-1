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
        customer.setCreateTime(LocalDateTime.now());
        customer.setEmail(registerPerson.getEmail());
        customer.setFirstName(registerPerson.getFirstName());
        customer.setLastName(registerPerson.getLastName());
        customer.setPesel(registerPerson.getPesel());

        String subj;
        String body;
        if (registerPerson.isVerified()) {
            customer.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Hi " + registerPerson.getFirstName() + "</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
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
        customer.setCompanyName(registerCompany.getName());
        customer.setCompanyVat(registerCompany.getVat());
        customer.setCreateTime(LocalDateTime.now());

        String subj;
        String body;
        if (registerCompany.isVerified()) {
            customer.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Your company: " + registerCompany.getName() + " is ready to make na order.</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            subj = "Waiting for verification";
            body = "<b>Hello</b><br/>" +
                "We registered your company: " + registerCompany.getName() + " in our service. Please wait for verification!";
        }
        dao.save(customer);
        mailSender.send(registerCompany.getEmail(), subj, body);

        return true;
    }

    private boolean companyExists(Email email, Vat vat) {
        return dao.emailExists(email) || dao.vatExists(vat);
    }

    public boolean updateAddress(UpdateAddress updateAddress) {
        return dao.findById(updateAddress.getCustomerId())
            .map(customer -> updateCustomerAddress(updateAddress, customer))
            .orElse(false);
    }

    private boolean updateCustomerAddress(UpdateAddress updateAddress, Customer customer) {
        customer.updateAddress(new Address(updateAddress.getStreet(),
            updateAddress.getCity(),
            updateAddress.getZipCode(),
            updateAddress.getCountryCode()));
        dao.save(customer);
        return true;
    }

}
