package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

public class RegisterPerson {

    private final Email email;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final boolean verified;

    public RegisterPerson(Email email, String firstName, String lastName, String pesel, boolean verified) {
        this.email = requireNonNull(email);
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.pesel = requireNonNull(pesel);
        this.verified = verified;
    }

    public Email getEmail() {
        return email;
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

    public boolean isVerified() {
        return verified;
    }
}
