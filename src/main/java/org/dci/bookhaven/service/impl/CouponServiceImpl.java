package org.dci.bookhaven.service.impl;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.repository.CouponRepository;
import org.dci.bookhaven.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private CouponRepository couponRepository;

    @Autowired
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon getByCode(String code) {
        return couponRepository.findByCode(code);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public String applyCouponMsg(String couponCode) {
        String msg = "";
        List<Coupon> coupons = couponRepository.findAll();
        Coupon coupon = couponRepository.findByCode(couponCode);
        if (coupon == null) {
            msg = "Coupon not found";
        }else if(coupon.getEndDate().isBefore(LocalDateTime.now())){
            msg = "Coupon expired";
        } else if(coupon.getStartDate().isAfter(LocalDateTime.now())){
            msg = "Coupon not yet valid";
        } else if(!coupon.isActive()){
            msg = "Coupon not active";
        } else{
            msg = "Coupon " + couponCode + "applied";
        }
        return msg;
    }

    @Override
    public boolean isValid(String couponCode){
        Coupon coupon = couponRepository.findByCode(couponCode);
        if (coupon == null) {
            return false;
        }else if(coupon.getEndDate().isBefore(LocalDateTime.now())){
            return false;
        } else if(coupon.getStartDate().isAfter(LocalDateTime.now())){
            return false;
        } else if(!coupon.isActive()){
            return false;
        } else{
            return true;
        }
    }

    @Override
    public Coupon create(Coupon coupon) {
        updateCouponStatus();
        for(Coupon c : couponRepository.findAll()){
            if(c.getCode().equals(coupon.getCode())){
                return null;
            }
        }
        return couponRepository.save(coupon);
    }

    @Override
    public void updateCouponStatus(){
        List<Coupon> coupons = couponRepository.findAll();
        for(Coupon coupon : coupons){
            if(coupon.getEndDate().isBefore(LocalDateTime.now())){
                coupon.setActive(false);
                couponRepository.save(coupon);
            }
        }
    }

    @Override
    public void deactivate(Long id){
        Coupon coupon = couponRepository.findById(id).get();
        coupon.setActive(false);
        couponRepository.save(coupon);
    }

    @Override
    public void reactivate(Long id){
        Coupon coupon = couponRepository.findById(id).get();
        coupon.setActive(true);
        couponRepository.save(coupon);
    }

    @Override
    public void delete(Long id){
        couponRepository.deleteById(id);
    }

    @Override
    public Coupon getById(Long id){
        return couponRepository.findById(id).get();
    }

}
