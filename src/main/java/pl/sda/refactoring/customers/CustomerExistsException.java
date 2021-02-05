package pl.sda.refactoring.customers;

public final class CustomerExistsException extends DomainException {

    public CustomerExistsException(String message) {
        super(message);
    }
}
