package org.dci.bookhaven.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Order;
import org.springframework.data.jpa.repository.Modifying;

public interface CheckoutService {
    @Modifying
    @Transactional
    PaymentIntent createPaymentIntent(Order order) throws StripeException, StripeException;
}
