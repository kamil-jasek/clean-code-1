package pl.sda.refactoring.customers;

import java.util.Optional;

public class DiscountCouponsDao {

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
