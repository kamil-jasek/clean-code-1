package pl.sda.refactoring.customers;

final class Name extends Value {

    Name(String value) {
        super(value);
    }

    static Name of(String value) {
        return new Name(value);
    }

    @Override
    protected void validate(String value) {
        if (!value.matches("[\\p{L}\\s\\.]{2,100}")) {
            throw new IllegalArgumentException("Invalid name: " + value);
        }
    }
}
