package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.service.impl.CouponServiceImpl;

public interface CouponService {


    Coupon findByCode(String code);

    void applyCoupon(Coupon coupon);
}
