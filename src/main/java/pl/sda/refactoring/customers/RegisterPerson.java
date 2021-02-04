package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

public class RegisterPerson {

    private final Email email;
    private final Name firstName;
    private final Name lastName;
    private final Pesel pesel;
    private final boolean verified;

    public RegisterPerson(Email email, Name firstName, Name lastName, Pesel pesel, boolean verified) {
        this.email = requireNonNull(email);
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.pesel = requireNonNull(pesel);
        this.verified = verified;
    }

    public Email getEmail() {
        return email;
    }

    public Name getFirstName() {
        return firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    public Pesel getPesel() {
        return pesel;
    }

    public boolean isVerified() {
        return verified;
    }
}
