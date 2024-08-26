package org.dci.bookhaven.service.impl;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.Value;
import org.dci.bookhaven.model.Payment;
import org.dci.bookhaven.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Value("${app.domain}")
    private String DOMAIN;

    @Value("${stripe.sk}")
    private String secretKey;

    StripeClient client = new StripeClient(secretKey);

    @Autowired
    public CheckoutServiceImpl() {
    }

    public PaymentIntent createPaymentIntent(Payment payment) throws StripeException, StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(Double.valueOf(payment.getAmount()).longValue() * 100) // amount in cents, amount has to be long type
                        .setCurrency(payment.getCurrency())
                        .setAutomaticPaymentMethods
                                (PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled
                                                (true).build())
                        .build();

        PaymentIntent paymentIntent = client.paymentIntents().create(params);
        return paymentIntent;
    }




}

