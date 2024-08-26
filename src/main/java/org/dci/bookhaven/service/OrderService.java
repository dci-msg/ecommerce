package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.dto.ShoppingCart;
import org.dci.bookhaven.model.*;
import org.springframework.data.jpa.repository.Modifying;

public interface OrderService {
    @Transactional
    @Modifying
    void createOrder(
            ShoppingCart shoppingCart,
            User user,
            Payment payment,
            Shipping shipping,
            Address billingAddress);

    // Update order status after the 30-day return period
    @Modifying
    @Transactional
    void closeOrder(Long orderId);
}
