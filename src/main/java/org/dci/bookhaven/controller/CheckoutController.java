package org.dci.bookhaven.controller;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.checkout.SessionCreateParams;
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

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderService orderService;

    @Value("${app.domain}")
    private String DOMAIN;

    @Autowired
    public CheckoutController(
            CheckoutService checkoutService,
            OrderService orderService) {
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }

//    @GetMapping("/checkout")
//    public String checkout(@RequestParam("orderId") Long orderId, Model model) throws StripeException {
//        // Fetch the order by orderId
//        Order order = orderService.getOrderById(orderId);
//
//        PaymentIntent intent = checkoutService.createPaymentIntent(order);
//        model.addAttribute("clientSecret", intent.getClientSecret());
//        return "checkout";
//    }

    @GetMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkout(@RequestParam("orderId") Long orderId) throws StripeException {
        // Fetch the order by orderId
        Order order = orderService.getOrderById(orderId);

        PaymentIntent intent = checkoutService.createPaymentIntent(order);
        Map<String, String> response = new HashMap<>();
        response.put("client_secret", intent.getClientSecret());
        return ResponseEntity.ok(response);
    }

}
