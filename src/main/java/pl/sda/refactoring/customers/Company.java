package pl.sda.refactoring.customers;

import java.util.Objects;

final class Company extends Customer {

    private final Name name;
    private final Vat vat;

    Company(Email email, Name name, Vat vat) {
        super(COMPANY, email);
        this.name = name;
        this.vat = vat;
    }

    public Name getName() {
        return name;
    }

    public Vat getVat() {
        return vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Company company = (Company) o;
        return name.equals(company.name) && vat.equals(company.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vat);
    }
}
