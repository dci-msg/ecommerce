package org.dci.bookhaven.controller;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.dci.bookhaven.model.Order;
import org.dci.bookhaven.service.CheckoutService;
import org.dci.bookhaven.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CheckoutController(
            CheckoutService checkoutService,
            OrderService orderService) {
        this.checkoutService = checkoutService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("orderId") Long orderId, Model model) throws StripeException{
        // Fetch the order by orderId
        Order order = orderService.getOrderById(orderId);

        PaymentIntent intent = checkoutService.createPaymentIntent(order);
        model.addAttribute("clientSecret", intent.getClientSecret());
        return "checkout";
    }


}
