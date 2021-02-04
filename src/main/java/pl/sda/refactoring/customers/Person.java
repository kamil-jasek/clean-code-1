package pl.sda.refactoring.customers;

import java.util.Objects;

final class Person extends Customer {

    private final Name firstName;
    private final Name lastName;
    private final Pesel pesel;

    Person(Email email, Name firstName, Name lastName, Pesel pesel) {
        super(PERSON, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
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
