package pl.sda.refactoring.customers.events;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import pl.sda.refactoring.application.events.Event;
import pl.sda.refactoring.customers.dto.CustomerVerification;

public final class RegisteredCompanyEvent implements Event {

    private final UUID id;
    private final String email;
    private final LocalDateTime createTime;
    private final String name;
    private final String vat;
    private final CustomerVerification verification;

    public RegisteredCompanyEvent(UUID id, String email, LocalDateTime createTime, String name, String vat,
        CustomerVerification verification) {
        this.id = requireNonNull(id);
        this.email = requireNonNull(email);
        this.createTime = requireNonNull(createTime);
        this.name = requireNonNull(name);
        this.vat = requireNonNull(vat);
        this.verification = verification;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getName() {
        return name;
    }

    public String getVat() {
        return vat;
    }

    public CustomerVerification getVerification() {
        return verification;
    }

    public boolean isVerified() {
        return verification != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegisteredCompanyEvent that = (RegisteredCompanyEvent) o;
        return id.equals(that.id) && email.equals(that.email) && createTime.equals(that.createTime) && name
            .equals(that.name) && vat.equals(that.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, createTime, name, vat);
    }
}
