package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.customers.dto.RegisterCompany;
import pl.sda.refactoring.customers.dto.RegisterPerson;
import pl.sda.refactoring.customers.dto.RegisteredCompany;
import pl.sda.refactoring.customers.dto.RegisteredPerson;
import pl.sda.refactoring.customers.dto.UpdateAddress;
import pl.sda.refactoring.customers.dto.UpdatedAddress;

public final class CustomerFacade {

    private final CustomerService service;

    public CustomerFacade(CustomerService service) {
        this.service = requireNonNull(service);
    }

    public RegisteredPerson registerPerson(RegisterPerson registerPerson) {
        return service.registerPerson(registerPerson);
    }

    public RegisteredCompany registerCompany(RegisterCompany registerCompany) {
        return service.registerCompany(registerCompany);
    }

    public UpdatedAddress updateAddress(UpdateAddress updateAddress) {
        return service.updateAddress(updateAddress);
    }
}
