package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    // Show coupon form for adding
    @GetMapping("/add")
    public String addCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "coupon/form-add";  // Refers to src/main/resources/templates/coupon/form-add.html
    }

    @PostMapping("/add")
    public String addCoupon(
            @RequestParam("code") String code,
            @RequestParam("discount") BigDecimal discount,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        Coupon coupon = new Coupon();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));

        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setStartDate(startDateTime);
        coupon.setEndDate(endDateTime);
        coupon.setActive(isActive);
        couponService.create(coupon);
        return "redirect:/coupon";
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

    @PostMapping("/edit/{id}")
    public String editCoupon(
            @PathVariable(value = "id", required = true) Long id,
            @RequestParam("code") String code,
            @RequestParam("discount") BigDecimal discount,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        Coupon coupon = new Coupon();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59));

        coupon.setId(id);
        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setStartDate(startDateTime);
        coupon.setEndDate(endDateTime);
        coupon.setActive(isActive);
        couponService.update(coupon);
        return "redirect:/coupon";
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
