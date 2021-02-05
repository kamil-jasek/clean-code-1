package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

final class RegisteredPersonEvent implements Event {

    private final UUID id;
    private final String email;
    private final LocalDateTime createTime;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final CustomerVerification verification;

    RegisteredPersonEvent(UUID id, String email, LocalDateTime createTime, String firstName, String lastName,
        String pesel, CustomerVerification verification) {
        this.id = requireNonNull(id);
        this.email = email;
        this.createTime = requireNonNull(createTime);
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.pesel = requireNonNull(pesel);
        this.verification = verification;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public CustomerVerification getVerification() {
        return verification;
    }

    public boolean isVerified() {
        return verification != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisteredPersonEvent that = (RegisteredPersonEvent) o;
        return id.equals(that.id) && createTime.equals(that.createTime) && firstName.equals(that.firstName) && lastName
            .equals(that.lastName) && pesel.equals(that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, firstName, lastName, pesel);
    }
}
