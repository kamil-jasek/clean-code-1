package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import pl.sda.refactoring.application.events.Event;
import pl.sda.refactoring.application.events.EventObserver;
import pl.sda.refactoring.customers.ExternalSystem.RegisteredCustomer;
import pl.sda.refactoring.customers.events.RegisteredCompanyEvent;
import pl.sda.refactoring.customers.events.RegisteredPersonEvent;

final class NotifyExternalSystemObserver implements EventObserver<Event> {

    private final ExternalSystem externalSystem;

    public NotifyExternalSystemObserver(ExternalSystem externalSystem) {
        this.externalSystem = requireNonNull(externalSystem);
    }

    @Override
    public boolean supports(Event event) {
        return event instanceof RegisteredCompanyEvent ||
            event instanceof RegisteredPersonEvent;
    }

    @Override
    public void handle(Event event) {
        if (event instanceof RegisteredCompanyEvent) {
            notifyWithCompanyRegisteredEvent((RegisteredCompanyEvent) event);
        } else {
            notifyWithPersonRegisteredEvent((RegisteredPersonEvent) event);
        }
    }

    private void notifyWithCompanyRegisteredEvent(RegisteredCompanyEvent event) {
        externalSystem.notifyAboutRegisteredCustomer(new RegisteredCustomer(
            event.getId(),
            event.getEmail(),
            event.getName(),
            event.getVat()));
    }

    private void notifyWithPersonRegisteredEvent(RegisteredPersonEvent event) {
        externalSystem.notifyAboutRegisteredCustomer(new RegisteredCustomer(
            event.getId(),
            event.getEmail(),
            event.getFirstName() + " " + event.getLastName(),
            event.getPesel()));
    }
}
