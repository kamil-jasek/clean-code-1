package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class CustomerServiceTest {

    private final CustomerDao dao = mock(CustomerDao.class);
    private final MailSender mailSender = mock(MailSender.class);
    private final CustomerService service = new CustomerService(dao, mailSender);

    @Test
    void shouldNotRegisterPersonWhenDataNotFilled() {
        // when
        final var result = service.registerPerson(null, null, null, null, false);

        // then
        assertFalse(result);
    }
}