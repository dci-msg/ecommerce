package org.dci.bookhaven.service.impl;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.repository.CouponRepository;
import org.dci.bookhaven.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCode(code);
    }


    @Override
    public void applyCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
