package org.dci.bookhaven.controller;


import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.checkout.SessionCreateParams;
import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.model.Order;
import org.dci.bookhaven.service.CheckoutService;
import org.dci.bookhaven.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Value("${app.domain}")
    private String DOMAIN;

    @Value("${stripe.sk}")
    private String secretKey;

    @GetMapping("/checkout")
    public String checkout(@RequestParam("orderId") Long orderId,
                           Model model) throws StripeException {

        // Fetch the order by orderId
        Order order =
        Set<LineItem> lineItems = order.getLineItems();
        Coupon coupon = order.getCoupon();
        String currency = order.getCurrency();

        PaymentIntent intent = checkoutService.createPaymentIntent(order);
        model.addAttribute("clientSecret", intent.getClientSecret());
        model.addAttribute("orderId", orderId);
        model.addAttribute("order", order);
        model.addAttribute("lineItems", lineItems);
        model.addAttribute("coupon", coupon);
        model.addAttribute("currency", currency);
        return "checkout";
    }

//    @GetMapping("/checkout")
//    public ResponseEntity<Map<String, String>> checkout(@RequestParam("orderId") Long orderId) throws StripeException {
//        // Fetch the order by orderId
//        Order order = orderService.getOrderById(orderId);
//
//        PaymentIntent intent = checkoutService.createPaymentIntent(order);
//        Map<String, String> response = new HashMap<>();
//        response.put("client_secret", intent.getClientSecret());
//        return ResponseEntity.ok(response);
//    }

}
