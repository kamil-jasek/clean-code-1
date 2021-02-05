package pl.sda.refactoring.customers;

final class ExternalSystemNoopImpl implements ExternalSystem {

    @Override
    public void notifyAboutRegisteredCustomer(RegisteredCustomer customer) {
    }
}
