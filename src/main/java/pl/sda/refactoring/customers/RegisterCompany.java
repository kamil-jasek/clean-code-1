package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

public class RegisterCompany {

    private final Email email;
    private final Name name;
    private final Vat vat;
    private final boolean verified;

    public RegisterCompany(Email email, Name name, Vat vat, boolean verified) {
        this.email = requireNonNull(email);
        this.name = requireNonNull(name);
        this.vat = requireNonNull(vat);
        this.verified = verified;
    }

    public Email getEmail() {
        return email;
    }

    public Name getName() {
        return name;
    }

    public Vat getVat() {
        return vat;
    }

    public boolean isVerified() {
        return verified;
    }
}
