package pl.sda.refactoring.customers.exceptions;

import pl.sda.refactoring.application.exceptions.DomainException;

public final class CustomerNotExistsException extends DomainException {

    public CustomerNotExistsException(String message) {
        super(message);
    }
}
