package pl.sda.refactoring.application.events;

public interface EventObserver<T extends Event> {

    boolean supports(Event event);
    void handle(T event);
}
