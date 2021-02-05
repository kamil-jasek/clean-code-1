package pl.sda.refactoring.application.events;

import java.util.ArrayList;
import java.util.List;

public final class EventPublisher {

    @SuppressWarnings("rawtypes")
    private final List<EventObserver> observers;

    public EventPublisher() {
        this.observers = new ArrayList<>();
    }

    public <T extends Event> void register(EventObserver<T> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public <T extends Event> boolean remove(EventObserver<T> observer) {
        return observers.remove(observer);
    }

    @SuppressWarnings("unchecked")
    public void publish(Event event) {
        observers.stream()
            .filter(observer -> observer.supports(event))
            .forEach(observer -> observer.handle(event));
    }
}
