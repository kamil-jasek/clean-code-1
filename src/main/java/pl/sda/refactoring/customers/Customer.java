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
    private CustomerVerification customerVerification;

    private Address address;

    void initCustomer(int type, Email email) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.createTime = LocalDateTime.now();
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
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

    public boolean isVerified() {
        return customerVerification != null;
    }

    public CustomerVerification getCustomerVerification() {
        return customerVerification;
    }

    void markVerified() {
        this.customerVerification = new CustomerVerification(LocalDateTime.now(), CustomerVerifier.AUTO_EMAIL);
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
        return type == customer.type && id.equals(customer.id) && createTime.equals(customer.createTime) && email
            .equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, createTime, email);
    }
}
