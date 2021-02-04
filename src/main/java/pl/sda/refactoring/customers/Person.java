package pl.sda.refactoring.customers;

import java.util.Objects;

final class Person extends Customer {

    private Name firstName;
    private Name lastName;
    private Pesel pesel;

    void initPerson(RegisterPerson registerPerson) {
        initCustomer(PERSON, registerPerson.getEmail());
        this.firstName = registerPerson.getFirstName();
        this.lastName = registerPerson.getLastName();
        this.pesel = registerPerson.getPesel();
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
        Person person = (Person) o;
        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && pesel.equals(person.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }
}
