package org.dci.bookhaven.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Order;
import org.dci.bookhaven.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Value("${stripe.sk}")
    private String secretKey;

    @Autowired
    public CheckoutServiceImpl(@Value("${stripe.sk}") String secretKey) {
        this.secretKey = secretKey;
        Stripe.apiKey = secretKey;
    }

    @Override
    @Modifying
    @Transactional
    public PaymentIntent createPaymentIntent(Order order) throws StripeException, StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(Double.valueOf(order.getTotal()).longValue() * 100) // amount in cents, amount has to be long type
                        .setCurrency(order.getCurrency())
                        .setAutomaticPaymentMethods
                                (PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled
                                                (true).build())
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    // Retrieve the client secret from the PaymentIntent object
    public String getClientSecret(PaymentIntent paymentIntent) {
        return paymentIntent.getClientSecret();
    }


}

