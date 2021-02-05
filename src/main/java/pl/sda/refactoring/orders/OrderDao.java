package pl.sda.refactoring.orders;

import java.util.Optional;
import java.util.UUID;

public class OrderDao {

    public void save(Order order) {
        throw new UnsupportedOperationException();
    }

    public Optional<Order> findById(UUID oid) {
        throw new UnsupportedOperationException();
    }

    public Optional<String> findCustomerEmail(UUID customerId) {
        throw new UnsupportedOperationException();
    }
}
