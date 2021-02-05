package pl.sda.refactoring.customers.exceptions;

import pl.sda.refactoring.application.exceptions.DomainException;

public final class CustomerExistsException extends DomainException {

    public CustomerExistsException(String message) {
        super(message);
    }
}
