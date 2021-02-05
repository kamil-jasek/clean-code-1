package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.application.events.EventPublisher;
import pl.sda.refactoring.customers.dto.Email;
import pl.sda.refactoring.customers.dto.Pesel;
import pl.sda.refactoring.customers.dto.RegisterCompany;
import pl.sda.refactoring.customers.dto.RegisterPerson;
import pl.sda.refactoring.customers.dto.RegisteredCompany;
import pl.sda.refactoring.customers.dto.RegisteredPerson;
import pl.sda.refactoring.customers.dto.UpdateAddress;
import pl.sda.refactoring.customers.dto.UpdatedAddress;
import pl.sda.refactoring.customers.dto.Vat;
import pl.sda.refactoring.customers.exceptions.CustomerExistsException;
import pl.sda.refactoring.customers.exceptions.CustomerNotExistsException;

class CustomerService {

    private final CustomerDao dao;
    private final EventPublisher eventPublisher;
    private final CustomerMapper mapper;

    public CustomerService(CustomerDao dao, EventPublisher eventPublisher, CustomerMapper mapper) {
        this.dao = requireNonNull(dao);
        this.eventPublisher = requireNonNull(eventPublisher);
        this.mapper = requireNonNull(mapper);
    }

    public RegisteredPerson registerPerson(RegisterPerson registerPerson) {
        if (personExists(registerPerson.getEmail(), registerPerson.getPesel())) {
            throw new CustomerExistsException("Email: " + registerPerson.getEmail() +
                ", or pesel: " + registerPerson.getPesel() + " already exists");
        }
        final var person = mapper.map(registerPerson);
        if (registerPerson.isVerified()) {
            person.markVerified();
        }
        dao.save(person);
        eventPublisher.publish(mapper.mapToRegisteredPersonEvent(person));
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
        if (registerCompany.isVerified()) {
            company.markVerified();
        }
        dao.save(company);
        eventPublisher.publish(mapper.mapToRegisteredCompanyEvent(company));
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
