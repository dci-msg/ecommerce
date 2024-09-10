package org.dci.bookhaven.service.impl;


import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Order;
import org.dci.bookhaven.repository.OrderRepository;
import org.dci.bookhaven.service.OrderService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        orders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return orders;
    }

    @Modifying
    @Transactional
    @Override
    public void create(Order order) {
        orderRepository.save(order);
    }


}
