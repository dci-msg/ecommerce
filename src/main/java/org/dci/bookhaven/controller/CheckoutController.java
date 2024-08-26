package org.dci.bookhaven.controller;


import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.dci.bookhaven.model.Payment;
import org.dci.bookhaven.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(
            @RequestBody Payment payment) throws StripeException {

        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(payment);
        String paymentStr = paymentIntent.toJson();

        return ResponseEntity.ok(paymentStr);
    }
}
