package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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

    @Test
    void shouldRegisterNotVerifiedPerson() {
        // when
        final var result = service.registerPerson("em@test.com", "Jan", "Kowalski", "92893202093", false);

        // then
        final var customer = verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.PERSON, customer.getType());
        assertNotNull(customer.getId());
        assertFalse(customer.isVerf());
        assertEquals("em@test.com", customer.getEmail());
        assertEquals("Jan", customer.getfName());
        assertEquals("Kowalski", customer.getlName());
        assertEquals("92893202093", customer.getPesel());
        assertNotNull(customer.getCtime());
    }

    @Test
    void shouldRegisterVerifiedPerson() {
        // when
        final var result = service.registerPerson("em@test.com", "Jan", "Kowalski", "92893202093", true);

        // then
        final var customer = verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.PERSON, customer.getType());
        assertNotNull(customer.getId());
        assertTrue(customer.isVerf());
        assertNotNull(customer.getVerfTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, customer.getVerifBy());
        assertEquals("em@test.com", customer.getEmail());
        assertEquals("Jan", customer.getfName());
        assertEquals("Kowalski", customer.getlName());
        assertEquals("92893202093", customer.getPesel());
        assertNotNull(customer.getCtime());
    }

    private Customer verifyCustomerSaved() {
        final var captor = ArgumentCaptor.forClass(Customer.class);
        verify(dao).save(captor.capture());
        return captor.getValue();
    }

}