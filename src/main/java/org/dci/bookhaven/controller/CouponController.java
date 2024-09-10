package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Show list of coupons
    @GetMapping("")
    public String viewAllCoupons(Model model) {
        List<Coupon> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "coupon/coupons";  // Refers to src/main/resources/templates/coupons/list.html
    }

    // Show coupon form for creating or editing
    @GetMapping("/add")
    public String addCouponForm(Model model) {
        Coupon coupon = new Coupon();
        model.addAttribute("coupon", coupon);
        return "coupon/form-add";  // Refers to src/main/resources/templates/coupons/form-add.html
    }

    @GetMapping("/updateStatus")
    public String updateStatus() {
        couponService.updateCouponStatus();
        return "redirect:/coupon";
    }

    // Show coupon form for editing
    @GetMapping("/edit/{id}")
    public String editCouponForm(Model model, @PathVariable(value = "id", required = true) Long id) {
        Coupon coupon = couponService.getById(id);
        model.addAttribute("coupon", coupon);
        return "coupon/form-edit";  // Refers to src/main/resources/templates/coupon/form-add.html
    }

    @GetMapping("/deactivate/{id}")
    public String deactivateCoupon(
            @PathVariable(value = "id", required = false) Long id) {
        couponService.deactivate(id);
        return "redirect:/coupon";
    }

    @GetMapping("/reactivate/{id}")
    public String reactivateCoupon(
            @PathVariable(value = "id", required = false) Long id) {
        couponService.reactivate(id);
        return "redirect:/coupon";
    }

    // Show coupon form for deleting
    @GetMapping("/delete/{id}")
    public String deleteCoupon(@RequestParam(value = "id", required = true) Long id) {
        couponService.delete(id);
        return "redirect:/coupon";
    }
}
