package pl.sda.refactoring.customers;

import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import pl.sda.refactoring.application.events.EventPublisher;

final class TestCustomerFacadeConfig {

    private final CustomerDao dao = mock(CustomerDao.class);

    CustomerFacade facade() {
        return new CustomerFacade(new CustomerService(dao, new EventPublisher(), new CustomerMapper()));
    }

    CustomerDao dao() {
        return dao;
    }
}
