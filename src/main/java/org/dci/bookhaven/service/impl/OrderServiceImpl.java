package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.dto.ShoppingCart;
import org.dci.bookhaven.model.*;
import org.dci.bookhaven.repository.OrderRepository;
import org.dci.bookhaven.service.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private CheckoutService checkoutService;
    private EmailService emailService;
    private PaymentService paymentService;
    private UserService userService;
    private ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    @Modifying
    public void createOrder(
            ShoppingCart shoppingCart,
            User user,
            Address billingAddress) {

        Order order = new Order();

        order.setUser(user);
        order.setLineItems(shoppingCart.getLineItems());
        order.setCoupon(shoppingCart.getCoupon());
        order.setTotal(shoppingCart.getTotal());
        order.setBillingAddress(billingAddress);
        order.setStatus("OPEN");

        orderRepository.save(order);
    }

    // Update order status after the 30-day return period automatically
    @Override
    @Modifying
    @Transactional
    public void closeOrder(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        order.setStatus("CLOSED");
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findOrderById(orderId);
    }


}
