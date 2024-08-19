package org.dci.bookhaven.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.dci.bookhaven.model.ShoppingCart;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    String YOUR_DOMAIN = "http://localhost:8080";

    public CheckoutController() {
        Stripe.apiKey = "your-secret-key";
    }

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Map<String, Object> requestData) throws StripeException {
        // Extract product details from requestData or define them directly
        SessionCreateParams params =
                SessionCreateParams.builder()
                        // Add an endpoint on your server that creates a Checkout Session, setting the ui_mode to embedded.
                        // The Checkout Session response includes a client secret, which the client uses to mount Checkout.
                        // Return the client_secret in your response.
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        // To handle different transaction types, adjust the mode parameter. One-off payments, use payment. Subscriptions, use subscription.
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setReturnUrl(YOUR_DOMAIN + "/return.html?session_id={CHECKOUT_SESSION_ID}")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                        .setPrice("{{PRICE_ID}}")
                                        .build())
                        .build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }
}
