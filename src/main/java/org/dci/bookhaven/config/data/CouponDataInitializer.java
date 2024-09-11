package org.dci.bookhaven.config.data;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.service.CouponService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Order(DataInitOrder.COUPON)
public class CouponDataInitializer implements CommandLineRunner {

    private final CouponService couponService;

    public CouponDataInitializer(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public void run(String... args) throws Exception {
        Coupon coupon1 = new Coupon();
        coupon1.setCode("DISCOUNT20");
        coupon1.setDiscount(BigDecimal.valueOf(20));
        coupon1.setActive(true);
        coupon1.setStartDate(LocalDateTime.of(2024, 9, 1, 0, 0));
        coupon1.setEndDate(LocalDateTime.of(2024, 9, 30, 23, 59));

        couponService.create(coupon1);

        Coupon coupon2 = new Coupon();
        coupon2.setCode("DISCOUNT30");
        coupon2.setDiscount(BigDecimal.valueOf(30));
        coupon2.setActive(true);
        coupon2.setStartDate(LocalDateTime.of(2024, 10, 1, 0, 0));
        coupon2.setEndDate(LocalDateTime.of(2024, 10, 31, 23, 59));

        couponService.create(coupon2);

        Coupon coupon3 = new Coupon();
        coupon3.setCode("INVALIDDEMO");
        coupon3.setDiscount(BigDecimal.valueOf(40));
        coupon3.setActive(false);
        coupon3.setStartDate(LocalDateTime.of(2024, 8, 1, 0, 0));
        coupon3.setEndDate(LocalDateTime.of(2024, 8, 31, 23, 59));

        couponService.create(coupon3);
    }
}
