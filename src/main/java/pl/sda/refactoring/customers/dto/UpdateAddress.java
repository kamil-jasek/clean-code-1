package pl.sda.refactoring.customers.dto;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

public class UpdateAddress {

    private final UUID customerId;
    private final String street;
    private final String zipCode;
    private final String city;
    private final String countryCode;

    public UpdateAddress(UUID customerId, String street, String zipCode, String city, String countryCode) {
        this.customerId = requireNonNull(customerId);
        this.street = requireNonNull(street);
        this.zipCode = requireNonNull(zipCode);
        this.city = requireNonNull(city);
        this.countryCode = requireNonNull(countryCode);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
