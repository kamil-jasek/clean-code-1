package pl.sda.refactoring.orders;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

class DiscountCoupon {

    private String coupon;
    private float value;
    private LocalDateTime validDate;
    private boolean used;
    private UUID usedBy;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public UUID getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(UUID usedBy) {
        this.usedBy = usedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountCoupon that = (DiscountCoupon) o;
        return Float.compare(that.value, value) == 0 && used == that.used && Objects
            .equals(coupon, that.coupon) && Objects.equals(usedBy, that.usedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coupon, value, used, usedBy);
    }

    public LocalDateTime getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDateTime validDate) {
        this.validDate = validDate;
    }
}
