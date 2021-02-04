package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * The customer, can be person or company
 */
public class Customer {

    // customer types
    public static final int COMPANY = 1;
    public static final int PERSON = 2;


    private UUID id;
    private int type;
    private LocalDateTime createTime;
    private Email email;
    private LocalDateTime verificationTime;
    private boolean verified;
    private CustomerVerifier verifier;

    // company data
    private Name companyName;
    private Vat companyVat;

    // person data
    private Name firstName;
    private Name lastName;
    private Pesel pesel;

    private Address address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Name getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Name companyName) {
        this.companyName = companyName;
    }

    public Vat getCompanyVat() {
        return companyVat;
    }

    public void setCompanyVat(Vat companyVat) {
        this.companyVat = companyVat;
    }

    public Name getFirstName() {
        return firstName;
    }

    public void setFirstName(Name firstName) {
        this.firstName = firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    public void setLastName(Name lastName) {
        this.lastName = lastName;
    }

    public Pesel getPesel() {
        return pesel;
    }

    public void setPesel(Pesel pesel) {
        this.pesel = pesel;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public LocalDateTime getVerificationTime() {
        return verificationTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public CustomerVerifier getVerifier() {
        return verifier;
    }

    void markVerified() {
        this.verified = true;
        this.verificationTime = LocalDateTime.now();
        this.verifier = CustomerVerifier.AUTO_EMAIL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return type == customer.type && verified == customer.verified && Objects.equals(id, customer.id)
            && Objects.equals(createTime, customer.createTime) && Objects.equals(email, customer.email)
            && Objects.equals(verificationTime, customer.verificationTime) && verifier == customer.verifier && Objects
            .equals(companyName, customer.companyName) && Objects.equals(companyVat, customer.companyVat) && Objects
            .equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects
            .equals(pesel, customer.pesel) && Objects.equals(address.getStreet(),
            customer.address.getStreet()) && Objects
            .equals(address.getCity(), customer.address.getCity()) && Objects.equals(
            address.getZipCode(),
            customer.address.getZipCode())
            && Objects.equals(address.getCountryCode(), customer.address.getCountryCode());
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(id, type, createTime, email, verificationTime, verified, verifier, companyName, companyVat, firstName,
                lastName, pesel, address.getStreet(),
                address.getCity(), address.getZipCode(), address.getCountryCode());
    }
}
