package pl.sda.refactoring.customers.dto;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.customers.dto.Email;
import pl.sda.refactoring.customers.dto.Name;
import pl.sda.refactoring.customers.dto.Vat;

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
