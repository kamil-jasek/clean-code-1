package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

class CustomerServiceTest {

    private final CustomerDao dao = mock(CustomerDao.class);
    private final MailSender mailSender = mock(MailSender.class);
    private final CustomerService service = new CustomerService(dao, mailSender);

    @Test
    void shouldRegisterNotVerifiedPerson() {
        // when
        final var result = service.registerPerson(
            new RegisterPerson(Email.of("em@test.com"),
                Name.of("Jan"),
                Name.of("Kowalski"),
                Pesel.of("92893202093"),
                false));

        // then
        final var person = (Person) verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.PERSON, person.getType());
        assertNotNull(person.getId());
        assertFalse(person.isVerified());
        assertEquals(Email.of("em@test.com"), person.getEmail());
        assertEquals(Name.of("Jan"), person.getFirstName());
        assertEquals(Name.of("Kowalski"), person.getLastName());
        assertEquals(Pesel.of("92893202093"), person.getPesel());
        assertNotNull(person.getCreateTime());
    }

    @Test
    void shouldRegisterVerifiedPerson() {
        // when
        final var result = service.registerPerson(
            new RegisterPerson(Email.of("em@test.com"),
                Name.of("Jan"),
                Name.of("Kowalski"),
                Pesel.of("92893202093"),
                true));

        // then
        final var person = (Person) verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.PERSON, person.getType());
        assertNotNull(person.getId());
        assertNotNull(person.getCreateTime());
        assertTrue(person.isVerified());
        assertNotNull(person.getCustomerVerification().getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, person.getCustomerVerification().getVerifier());
        assertEquals(Email.of("em@test.com"), person.getEmail());
        assertEquals(Name.of("Jan"), person.getFirstName());
        assertEquals(Name.of("Kowalski"), person.getLastName());
        assertEquals(Pesel.of("92893202093"), person.getPesel());
    }

    @Test
    void shouldRegisterNotVerifiedCompany() {
        // when
        final var result = service.registerCompany(
            new RegisterCompany(Email.of("em@test.com"), Name.of("Test S.A."), Vat.of("8384783833"), false));

        // then
        final var customer = (Company) verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.COMPANY, customer.getType());
        assertNotNull(customer.getId());
        assertNotNull(customer.getCreateTime());
        assertFalse(customer.isVerified());
        assertEquals(Email.of("em@test.com"), customer.getEmail());
        assertEquals(Name.of("Test S.A."), customer.getCompanyName());
        assertEquals(Vat.of("8384783833"), customer.getCompanyVat());
    }

    @Test
    void shouldRegisterVerifiedCompany() {
        // when
        final var result = service.registerCompany(
            new RegisterCompany(Email.of("em@test.com"), Name.of("Test S.A."), Vat.of("8384783833"), true));

        // then
        final var customer = (Company) verifyCustomerSaved();
        assertTrue(result);
        assertEquals(Customer.COMPANY, customer.getType());
        assertNotNull(customer.getId());
        assertNotNull(customer.getCreateTime());
        assertTrue(customer.isVerified());
        assertNotNull(customer.getCustomerVerification().getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, customer.getCustomerVerification().getVerifier());
        assertEquals(Email.of("em@test.com"), customer.getEmail());
        assertEquals(Name.of("Test S.A."), customer.getCompanyName());
        assertEquals(Vat.of("8384783833"), customer.getCompanyVat());
    }

    @Test
    void shouldNotUpdateAddressIfCustomerNotExists() {
        // given
        given(dao.findById(any())).willReturn(Optional.empty());

        // when
        final var result = service.updateAddress(new UpdateAddress(UUID.randomUUID(),
            "str",
            "02-303",
            "Wawa",
            "PL"));

        // then
        assertFalse(result);
    }

    @Test
    void shouldUpdateAddress() {
        // given
        given(dao.findById(any())).willReturn(Optional.of(new Customer()));

        // when
        final var result = service.updateAddress(new UpdateAddress(UUID.randomUUID(),
            "str",
            "02-303",
            "Wawa",
            "PL"));

        // then
        final var customer = verifyCustomerSaved();
        assertTrue(result);
        assertEquals(new Address("str", "Wawa", "02-303", "PL"), customer.getAddress());
    }

    private Customer verifyCustomerSaved() {
        final var captor = ArgumentCaptor.forClass(Customer.class);
        verify(dao).save(captor.capture());
        return captor.getValue();
    }

}