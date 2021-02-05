package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

public class CustomerService {

    private final CustomerDao dao;
    private final MailSender mailSender;
    private final CustomerMapper mapper;

    public CustomerService(CustomerDao dao, MailSender mailSender, CustomerMapper mapper) {
        this.dao = requireNonNull(dao);
        this.mailSender = requireNonNull(mailSender);
        this.mapper = requireNonNull(mapper);
    }

    public RegisteredPerson registerPerson(RegisterPerson registerPerson) {
        if (personExists(registerPerson.getEmail(), registerPerson.getPesel())) {
            throw new CustomerExistsException("Email: " + registerPerson.getEmail() +
                ", or pesel: " + registerPerson.getPesel() + " already exists");
        }

        final var person = mapper.map(registerPerson);

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
        return mapper.mapToRegisteredPerson(person);
    }

    private boolean personExists(Email email, Pesel pesel) {
        return dao.emailExists(email) || dao.peselExists(pesel);
    }

    public RegisteredCompany registerCompany(RegisterCompany registerCompany) {
        if (companyExists(registerCompany.getEmail(), registerCompany.getVat())) {
            throw new CustomerExistsException("Email: " + registerCompany.getEmail() +
                " or VAT: " + registerCompany.getVat() + " already exists");
        }

        final var company = mapper.map(registerCompany);

        String subj;
        String body;
        if (registerCompany.isVerified()) {
            company.markVerified();
            subj = "Your are now verified customer!";
            body = "<b>Your company: " + registerCompany.getName() + " is ready to make na order.</b><br/>" +
                "Thank you for registering in our service. Now you are verified customer!";
        } else {
            subj = "Waiting for verification";
            body = "<b>Hello</b><br/>" +
                "We registered your company: " + registerCompany.getName() + " in our service. Please wait for verification!";
        }
        dao.save(company);
        mailSender.send(registerCompany.getEmail(), subj, body);

        return mapper.mapToRegisteredCompany(company);
    }

    private boolean companyExists(Email email, Vat vat) {
        return dao.emailExists(email) || dao.vatExists(vat);
    }

    public UpdatedAddress updateAddress(UpdateAddress updateAddress) {
        final var customer = dao
            .findById(updateAddress.getCustomerId())
            .orElseThrow(() -> new CustomerNotExistsException(
                "Cannot find customer with id: " + updateAddress.getCustomerId()));
        customer.updateAddress(mapper.mapAddress(updateAddress));
        dao.save(customer);
        return mapper.mapToUpdatedAddress(updateAddress);
    }

}
