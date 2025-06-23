package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> getAllOrderItems();
    Optional<OrderItem> getOrderItemById(Long id);
    OrderItem updateOrderItem(Long id, OrderItem orderItem);
    void deleteOrderItem(Long id);
}

