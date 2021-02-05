package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class CustomerServiceTest {

    private final CustomerDao dao = mock(CustomerDao.class);
    private final MailSender mailSender = mock(MailSender.class);
    private final CustomerService service = new CustomerService(dao, mailSender);

    @Test
    void shouldNotRegisterPersonWhenAlreadyExists() {
        // given
        given(dao.emailExists(any())).willReturn(true);
        given(dao.peselExists(any())).willReturn(true);

        // when
        assertThrows(CustomerExistsException.class, () -> service.registerPerson(new RegisterPerson(
            Email.of("em@test.com"),
            Name.of("Jan"),
            Name.of("Kowalski"),
            Pesel.of("92893202093"),
            false)));
    }

    @Test
    void shouldRegisterNotVerifiedPerson() {
        // when
        final var registeredPerson = service.registerPerson(
            new RegisterPerson(Email.of("em@test.com"),
                Name.of("Jan"),
                Name.of("Kowalski"),
                Pesel.of("92893202093"),
                false));

        // then
        assertNotNull(registeredPerson);
        assertNotNull(registeredPerson.getId());
        assertFalse(registeredPerson.isVerified());
        assertEquals("em@test.com", registeredPerson.getEmail());
        assertEquals("Jan", registeredPerson.getFirstName());
        assertEquals("Kowalski", registeredPerson.getLastName());
        assertEquals("92893202093", registeredPerson.getPesel());
        assertNotNull(registeredPerson.getCreateTime());
    }

    @Test
    void shouldRegisterVerifiedPerson() {
        // when
        final var registeredPerson = service.registerPerson(
            new RegisterPerson(Email.of("em@test.com"),
                Name.of("Jan"),
                Name.of("Kowalski"),
                Pesel.of("92893202093"),
                true));

        // then
        assertNotNull(registeredPerson.getId());
        assertNotNull(registeredPerson.getCreateTime());
        assertTrue(registeredPerson.isVerified());
        assertNotNull(registeredPerson.getVerification().getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, registeredPerson.getVerification().getVerifier());
        assertEquals("em@test.com", registeredPerson.getEmail());
        assertEquals("Jan", registeredPerson.getFirstName());
        assertEquals("Kowalski", registeredPerson.getLastName());
        assertEquals("92893202093", registeredPerson.getPesel());
    }

    @Test
    void shouldNotRegisterCompanyIfExists() {
        // given
        given(dao.emailExists(any())).willReturn(true);
        given(dao.vatExists(any())).willReturn(true);

        // when
        assertThrows(CustomerExistsException.class, () -> service.registerCompany(new RegisterCompany(
            Email.of("em@test.com"),
            Name.of("Test S.A."),
            Vat.of("8384783833"),
            false)));
    }

    @Test
    void shouldRegisterNotVerifiedCompany() {
        // when
        final var registeredCompany = service.registerCompany(
            new RegisterCompany(Email.of("em@test.com"),
                Name.of("Test S.A."),
                Vat.of("8384783833"),
                false));

        // then
        assertNotNull(registeredCompany);
        assertNotNull(registeredCompany.getId());
        assertNotNull(registeredCompany.getCreateTime());
        assertFalse(registeredCompany.isVerified());
        assertEquals("em@test.com", registeredCompany.getEmail());
        assertEquals("Test S.A.", registeredCompany.getName());
        assertEquals("8384783833", registeredCompany.getVat());
    }

    @Test
    void shouldRegisterVerifiedCompany() {
        // when
        final var customer = service.registerCompany(new RegisterCompany(
            Email.of("em@test.com"),
            Name.of("Test S.A."),
            Vat.of("8384783833"),
            true));

        // then
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertNotNull(customer.getCreateTime());
        assertTrue(customer.isVerified());
        assertNotNull(customer.getVerification().getVerificationTime());
        assertEquals(CustomerVerifier.AUTO_EMAIL, customer.getVerification().getVerifier());
        assertEquals("em@test.com", customer.getEmail());
        assertEquals("Test S.A.", customer.getName());
        assertEquals("8384783833", customer.getVat());
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
        given(dao.findById(any())).willReturn(Optional.of(new Person(Email.of("em@test.com"),
            Name.of("test"),
            Name.of("test"),
            Pesel.of("19393929329"))));

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