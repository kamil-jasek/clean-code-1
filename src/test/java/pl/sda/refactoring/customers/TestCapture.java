package pl.sda.refactoring.customers;

abstract class TestCapture<T> {

    private T capture;

    protected void setCapture(T capture) {
        this.capture = capture;
    }

    public T getCapture() {
        return capture;
    }

    void reset() {
        this.capture = null;
    }
}
