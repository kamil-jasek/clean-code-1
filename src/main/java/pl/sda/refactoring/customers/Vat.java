package pl.sda.refactoring.customers;

final class Vat extends Value {

    protected Vat(String value) {
        super(value);
    }

    static Vat of(String value) {
        return new Vat(value);
    }

    @Override
    protected void validate(String value) {
        if (!value.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid vat: " + value);
        }
    }
}
