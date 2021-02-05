package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

public class UpdatedAddress {

    private final UUID customerId;
    private final String street;
    private final String zipCode;
    private final String city;
    private final String countryCode;

    public UpdatedAddress(UUID customerId, String street, String zipCode, String city, String countryCode) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdatedAddress that = (UpdatedAddress) o;
        return customerId.equals(that.customerId) && street.equals(that.street) && zipCode.equals(that.zipCode) && city
            .equals(that.city) && countryCode.equals(that.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, street, zipCode, city, countryCode);
    }
}
