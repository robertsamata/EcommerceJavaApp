package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}
