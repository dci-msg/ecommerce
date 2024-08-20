package org.dci.bookhaven.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    String YOUR_DOMAIN = "http://localhost:4242";

    public CheckoutController() {
        Stripe.apiKey = "sk_test_51PniUQL6UNiuiJrz3evmG6jsYeX2t6cX9jamtzA2abCihZuCBqO73uSNeeuzaWyuPfWVnTZSmWgIsksDcAWprwuH00XcXZqkZ0";
    }

   @PostMapping("/create-checkout-session")
   public Map<String, String> createCheckoutSession(
                   @RequestBody Map<String, Object> requestData) throws StripeException {
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPrice("{{PRICE_ID}}")
                .setQuantity(1L)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(YOUR_DOMAIN+"/success.html")
                .setCancelUrl(YOUR_DOMAIN+"/cancel.html")
                .addLineItem(lineItem)
                .build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }
}

