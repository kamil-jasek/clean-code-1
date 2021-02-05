package pl.sda.refactoring.customers;

import pl.sda.refactoring.customers.dto.RegisterCompany;
import pl.sda.refactoring.customers.dto.RegisterPerson;
import pl.sda.refactoring.customers.dto.RegisteredCompany;
import pl.sda.refactoring.customers.dto.RegisteredPerson;
import pl.sda.refactoring.customers.dto.UpdateAddress;
import pl.sda.refactoring.customers.dto.UpdatedAddress;
import pl.sda.refactoring.customers.events.RegisteredCompanyEvent;
import pl.sda.refactoring.customers.events.RegisteredPersonEvent;

final class CustomerMapper {

    Person map(RegisterPerson registerPerson) {
        return new Person(registerPerson.getEmail(),
            registerPerson.getFirstName(),
            registerPerson.getLastName(),
            registerPerson.getPesel());
    }

    RegisteredPerson mapToRegisteredPerson(Person person) {
        return new RegisteredPerson(person.getId(),
            person.getEmail().getValue(),
            person.getCreateTime(),
            person.getFirstName().getValue(),
            person.getLastName().getValue(),
            person.getPesel().getValue(),
            person.getCustomerVerification());
    }

    Company map(RegisterCompany registerCompany) {
        return new Company(registerCompany.getEmail(),
            registerCompany.getName(),
            registerCompany.getVat());
    }

    RegisteredCompany mapToRegisteredCompany(Company company) {
        return new RegisteredCompany(company.getId(),
            company.getEmail().getValue(),
            company.getCreateTime(),
            company.getName().getValue(),
            company.getVat().getValue(),
            company.getCustomerVerification());
    }

    Address mapAddress(UpdateAddress updateAddress) {
        return new Address(updateAddress.getStreet(),
            updateAddress.getCity(),
            updateAddress.getZipCode(),
            updateAddress.getCountryCode());
    }

    UpdatedAddress mapToUpdatedAddress(UpdateAddress updateAddress) {
        return new UpdatedAddress(updateAddress.getCustomerId(),
            updateAddress.getStreet(),
            updateAddress.getZipCode(),
            updateAddress.getCity(),
            updateAddress.getCountryCode());
    }

    RegisteredPersonEvent mapToRegisteredPersonEvent(Person person) {
        return new RegisteredPersonEvent(person.getId(),
            person.getEmail().getValue(),
            person.getCreateTime(),
            person.getFirstName().getValue(),
            person.getLastName().getValue(),
            person.getPesel().getValue(),
            person.getCustomerVerification());
    }

    RegisteredCompanyEvent mapToRegisteredCompanyEvent(Company company) {
        return new RegisteredCompanyEvent(company.getId(),
            company.getEmail().getValue(),
            company.getCreateTime(),
            company.getName().getValue(),
            company.getVat().getValue(),
            company.getCustomerVerification());
    }
}
