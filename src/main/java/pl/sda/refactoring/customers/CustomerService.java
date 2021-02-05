package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

public class CustomerService {

    private final CustomerDao dao;
    private final MailSender mailSender;

    public CustomerService(CustomerDao dao, MailSender mailSender) {
        this.dao = requireNonNull(dao);
        this.mailSender = requireNonNull(mailSender);
    }

    public RegisteredPerson registerPerson(RegisterPerson registerPerson) {
        if (personExists(registerPerson.getEmail(), registerPerson.getPesel())) {
            throw new CustomerExistsException("Email: " + registerPerson.getEmail() +
                ", or pesel: " + registerPerson.getPesel() + " already exists");
        }

        final var person = new Person(registerPerson.getEmail(),
            registerPerson.getFirstName(),
            registerPerson.getLastName(),
            registerPerson.getPesel());

        String subj;
        String body;
        if (registerPerson.isVerified()) {
            person.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Hi " + registerPerson.getFirstName() + "</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            subj = "Waiting for verification";
            body = "<b>Hi " + registerPerson.getFirstName() + "</b><br/>" +
                "We registered you in our service. Please wait for verification!";
        }
        dao.save(person);
        mailSender.send(registerPerson.getEmail(), subj, body);

        return new RegisteredPerson(person.getId(),
            person.getEmail().getValue(),
            person.getCreateTime(),
            person.getFirstName().getValue(),
            person.getLastName().getValue(),
            person.getPesel().getValue(),
            person.getCustomerVerification());
    }

    private boolean personExists(Email email, Pesel pesel) {
        return dao.emailExists(email) || dao.peselExists(pesel);
    }

    public boolean registerCompany(RegisterCompany registerCompany) {
        if (companyExists(registerCompany.getEmail(), registerCompany.getVat())) {
            return false;
        }

        var customer = new Company(registerCompany.getEmail(),
            registerCompany.getName(),
            registerCompany.getVat());

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
