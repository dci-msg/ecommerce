package org.dci.bookhaven.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("/webhook")
public class StripeWebhookController {

    private static final String endpointSecret = "your-webhook-secret";

    @PostMapping
    public void handleWebhook(HttpServletRequest request) throws IOException, SignatureVerificationException {
        String payload = "";
        String sigHeader = request.getHeader("Stripe-Signature");

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                payload += line;
            }
        }

        Event event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );

        switch (event.getType()) {
            case "checkout.session.completed":
                // Handle the checkout.session.completed event
                break;
            // Handle other event types as necessary
            default:
                // Unexpected event type
        }
    }
}