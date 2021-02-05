package pl.sda.refactoring.customers;

public interface EventObserver<T extends Event> {

    boolean supports(Event event);
    void handle(T event);
}
