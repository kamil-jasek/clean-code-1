package pl.sda.refactoring.customers;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.UUID;

interface ExternalSystem {

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegisteredCustomer that = (RegisteredCustomer) o;
            return id.equals(that.id) && email.equals(that.email) && name.equals(that.name) && taxId.equals(that.taxId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, email, name, taxId);
        }
    }

    void notifyAboutRegisteredCustomer(RegisteredCustomer customer);
}
