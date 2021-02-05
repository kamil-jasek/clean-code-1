package pl.sda.refactoring.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import pl.sda.refactoring.application.events.EventPublisher;
import pl.sda.refactoring.customers.dto.CustomerVerifier;
import pl.sda.refactoring.customers.dto.Email;
import pl.sda.refactoring.customers.dto.Name;
import pl.sda.refactoring.customers.dto.Pesel;
import pl.sda.refactoring.customers.dto.RegisterCompany;
import pl.sda.refactoring.customers.dto.RegisterPerson;
import pl.sda.refactoring.customers.dto.UpdateAddress;
import pl.sda.refactoring.customers.dto.UpdatedAddress;
import pl.sda.refactoring.customers.dto.Vat;
import pl.sda.refactoring.customers.exceptions.CustomerExistsException;
import pl.sda.refactoring.customers.exceptions.CustomerNotExistsException;

class CustomerServiceTest {

    private final CustomerDao dao = mock(CustomerDao.class);
    private final CustomerService service = new CustomerService(dao, new EventPublisher(), new CustomerMapper());

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
        assertThrows(CustomerNotExistsException.class, () -> service.updateAddress(new UpdateAddress(UUID.randomUUID(),
            "str",
            "02-303",
            "Wawa",
            "PL")));
    }

    @Test
    void shouldUpdateAddress() {
        // given
        given(dao.findById(any())).willReturn(Optional.of(new Person(Email.of("em@test.com"),
            Name.of("test"),
            Name.of("test"),
            Pesel.of("19393929329"))));
        final var customerId = UUID.fromString("df55ad5d-6d50-4e52-8599-b4abb23a27d1");

        // when
        final var updatedAddress = service.updateAddress(new UpdateAddress(customerId,
            "str",
            "02-303",
            "Wawa",
            "PL"));

        // then
        assertNotNull(updatedAddress);
        assertEquals(new UpdatedAddress(customerId, "str", "02-303", "Wawa", "PL"), updatedAddress);
    }
}