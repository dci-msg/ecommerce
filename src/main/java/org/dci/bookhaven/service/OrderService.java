package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.dto.ShoppingCart;
import org.dci.bookhaven.model.*;
import org.springframework.data.jpa.repository.Modifying;

public interface OrderService {

    @Transactional
    @Modifying
    void createFromCart(
            ShoppingCart shoppingCart,
            User user,
            Address billingAddress);

    @Transactional
    @Modifying
    void createOrder(Order order);

    // Update order status after the 30-day return period
    @Modifying
    @Transactional
    void closeOrder(Long orderId);

    Order getOrderById(Long orderId);
}
