package pl.sda.refactoring.customers;

import java.util.Optional;
import java.util.UUID;

public class CustomerDao {

    public void save(Customer customer) {
        throw new UnsupportedOperationException();
    }

    public boolean emailExists(Email email) {
        throw new UnsupportedOperationException();
    }

    public boolean peselExists(Pesel pesel) {
        throw new UnsupportedOperationException();
    }

    public boolean vatExists(Vat vat) {
        throw new UnsupportedOperationException();
    }

    public Optional<Customer> findById(UUID cid) {
        throw new UnsupportedOperationException();
    }
}
