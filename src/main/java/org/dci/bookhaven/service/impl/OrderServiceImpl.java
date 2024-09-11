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


    @Override
    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Modifying
    @Transactional
    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Modifying
    @Transactional
    @Override
    public void close(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus("Closed");
            orderRepository.save(order);
        }
    }

    @Modifying
    @Transactional
    @Override
    public void reopen(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus("Open");
            orderRepository.save(order);
        }
    }
}
