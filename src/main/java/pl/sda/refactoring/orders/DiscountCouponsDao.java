package pl.sda.refactoring.orders;

import java.util.Optional;

class DiscountCouponsDao {

    public Optional<DiscountCoupon> findByCode(String code) {
        throw new UnsupportedOperationException();
    }

    public boolean couponExists(String code) {
        throw new UnsupportedOperationException();
    }

    public void save(DiscountCoupon coupon) {
        throw new UnsupportedOperationException();
    }
}
