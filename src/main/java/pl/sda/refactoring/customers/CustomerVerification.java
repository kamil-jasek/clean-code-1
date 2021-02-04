package pl.sda.refactoring.customers;

import java.time.LocalDateTime;
import java.util.Objects;

final class CustomerVerification {

    private final boolean verified;
    private final LocalDateTime verificationTime;
    private final CustomerVerifier verifier;

    public CustomerVerification(LocalDateTime verificationTime,
        CustomerVerifier verifier) {
        this.verified = true;
        this.verificationTime = verificationTime;
        this.verifier = verifier;
    }

    public LocalDateTime getVerificationTime() {
        return verificationTime;
    }

    public boolean isVerified() {
        return verified;
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
        return verified == that.verified && verificationTime.equals(that.verificationTime) && verifier == that.verifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(verified, verificationTime, verifier);
    }
}