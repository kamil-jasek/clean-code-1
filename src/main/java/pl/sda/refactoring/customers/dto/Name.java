package pl.sda.refactoring.customers.dto;

public final class Name extends Value {

    Name(String value) {
        super(value);
    }

    public static Name of(String value) {
        return new Name(value);
    }

    @Override
    protected void validate(String value) {
        if (!value.matches("[\\p{L}\\s\\.]{2,100}")) {
            throw new IllegalArgumentException("Invalid name: " + value);
        }
    }
}
