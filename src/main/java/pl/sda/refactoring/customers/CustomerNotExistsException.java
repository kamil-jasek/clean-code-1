package pl.sda.refactoring.customers;

public final class CustomerNotExistsException extends DomainException {

    public CustomerNotExistsException(String message) {
        super(message);
    }
}
