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
@RequestMapping("/coupons")
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

    @GetMapping("/coupon/{id}")
    public String viewCouponDetails(
            @PathVariable("id") Long id,
            Model model) {
        Coupon coupon = couponService.getById(id);
        model.addAttribute("coupon", coupon);
        return "coupon/coupon-detail";  // Refers to src/main/resources/templates/coupon/coupon.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        model.addAttribute("startDate", LocalDate.now());
        model.addAttribute("endDate", LocalDate.now());
        model.addAttribute("isActive", true);
        return "coupon/form-add";
    }

    @PostMapping("/add")
    public String addCoupon(@ModelAttribute("coupon") Coupon coupon,
                            @ModelAttribute("startDate") LocalDate startDate,
                            @ModelAttribute("endDate") LocalDate endDate,
                            @ModelAttribute("isActive") boolean isActive) {
        coupon.setActive(isActive);
        coupon.setStartDate(LocalDateTime.of(startDate, LocalTime.MIDNIGHT));
        coupon.setEndDate(LocalDateTime.of(endDate, LocalTime.MIDNIGHT));
        couponService.create(coupon);
        return "redirect:/coupons";
    }

    @GetMapping("/updateStatus")
    public String updateStatus() {
        couponService.updateCouponStatus();
        return "redirect:/coupons";
    }

    @GetMapping("/coupon/{id}/edit")
    public String showEditForm(Model model, @PathVariable("id") Long id) {
        Coupon coupon = couponService.getById(id);
        model.addAttribute("coupon", coupon);
        model.addAttribute("startDate", coupon.getStartDate());
        model.addAttribute("endDate", coupon.getEndDate());
        model.addAttribute("isActive", coupon.isActive());
        return "coupon/form-edit";
    }

    @PostMapping("/coupon/{id}/edit")
    public String editCoupon(@PathVariable("id") Long id,
                             @ModelAttribute("coupon") Coupon coupon,
                             @RequestParam("code") String code,
                             @RequestParam("discount") BigDecimal discount,
                             @RequestParam("isActive") boolean isActive,
                             @RequestParam("startDate") LocalDateTime startDate,
                             @RequestParam("endDate") LocalDateTime endDate) {

        coupon.setId(id);
        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setStartDate(startDate);
        coupon.setEndDate(endDate);
        coupon.setActive(isActive);
        couponService.update(coupon);
        return "redirect:/coupons";
    }

    @GetMapping("/coupon/{id}/deactivate")
    public String deactivateCoupon(
            @PathVariable(value = "id", required = false) Long id) {
        couponService.deactivate(id);
        return "redirect:/coupons";
    }

    @GetMapping("/coupon/{id}/reactivate")
    public String reactivateCoupon(
            @PathVariable(value = "id", required = false) Long id) {
        couponService.reactivate(id);
        return "redirect:/coupons";
    }

    // Show coupon form for deleting
    @GetMapping("/coupon/{id}/delete")
    public String deleteCoupon(@PathVariable(value = "id", required = true) Long id) {
        couponService.delete(id);
        return "redirect:/coupons";
    }
}
