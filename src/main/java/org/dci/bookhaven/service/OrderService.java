package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Order;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    @Modifying
    @Transactional
    void create(Order order);

    Order getOrderById(long id);

    @Modifying
    @Transactional
    void update(Order order);

    @Modifying
    @Transactional
    void close(long id);

    @Modifying
    @Transactional
    void reopen(long id);
}
