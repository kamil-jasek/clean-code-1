package pl.sda.refactoring.customers.dto;

public final class Pesel extends Value {

    protected Pesel(String value) {
        super(value);
    }

    public static Pesel of(String value) {
        return new Pesel(value);
    }

    @Override
    protected void validate(String value) {
        if (!value.matches("\\d{11}")) {
            throw new IllegalArgumentException("Pesel is invalid: " + value);
        }
    }
}
