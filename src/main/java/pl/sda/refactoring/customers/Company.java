package pl.sda.refactoring.customers;

import java.util.Objects;

final class Company extends Customer {

    // company data
    private Name companyName;
    private Vat companyVat;

    void initCompany(RegisterCompany registerCompany) {
        initCustomer(COMPANY, registerCompany.getEmail());
        this.companyName = registerCompany.getName();
        this.companyVat = registerCompany.getVat();
    }

    public Name getCompanyName() {
        return companyName;
    }

    public Vat getCompanyVat() {
        return companyVat;
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
        return companyName.equals(company.companyName) && companyVat.equals(company.companyVat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, companyVat);
    }
}
