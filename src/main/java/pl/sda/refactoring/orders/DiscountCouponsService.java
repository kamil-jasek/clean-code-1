package pl.sda.refactoring.orders;

import java.time.LocalDateTime;

class DiscountCouponsService {

    private DiscountCouponsDao dao;

    public boolean createCoupon(String code, LocalDateTime date) {
        var result = false;
        final var bool = dao.couponExists(code);
        if (!bool) {
            var coupon = new DiscountCoupon();
            coupon.setCoupon(code);
            coupon.setValidDate(date);
            coupon.setUsed(false);
            dao.save(coupon);
            result = true;
        }
        return result;
    }

    public void setDao(DiscountCouponsDao dao) {
        this.dao = dao;
    }
}
