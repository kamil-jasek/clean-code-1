package pl.sda.refactoring.customers;

import pl.sda.refactoring.customers.ExternalSystem.RegisteredCustomer;

final class TestExternalSystem extends TestCapture<RegisteredCustomer> implements ExternalSystem {

    @Override
    public void notifyAboutRegisteredCustomer(RegisteredCustomer customer) {
        setCapture(customer);
    }
}
