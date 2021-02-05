package pl.sda.refactoring.customers.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public final class CustomerVerification {

    private final LocalDateTime verificationTime;
    private final CustomerVerifier verifier;

    public CustomerVerification(LocalDateTime verificationTime,
        CustomerVerifier verifier) {
        this.verificationTime = verificationTime;
        this.verifier = verifier;
    }

    public LocalDateTime getVerificationTime() {
        return verificationTime;
    }

    public CustomerVerifier getVerifier() {
        return verifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerVerification that = (CustomerVerification) o;
        return verificationTime.equals(that.verificationTime) && verifier == that.verifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verificationTime, verifier);
    }
}