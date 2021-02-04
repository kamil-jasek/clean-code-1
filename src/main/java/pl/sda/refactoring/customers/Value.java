package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

abstract class Value {

    private final String value;

    protected Value(String value) {
        this.value = requireNonNull(value);
        validate(value);
    }

    protected abstract void validate(String value);

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Value value1 = (Value) o;
        return value.equals(value1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
