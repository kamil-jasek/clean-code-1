package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

public interface ExternalSystem {

    class RegisteredCustomer {
        private final UUID id;
        private final String email;
        private final String name;
        private final String taxId;

        public RegisteredCustomer(UUID id, String email, String name, String taxId) {
            this.id = requireNonNull(id);
            this.email = requireNonNull(email);
            this.name = requireNonNull(name);
            this.taxId = requireNonNull(taxId);
        }

        public UUID getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getTaxId() {
            return taxId;
        }
    }

    void notifyAboutRegisteredCustomer(RegisteredCustomer customer);
}
