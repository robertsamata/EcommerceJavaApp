package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.OrderItem;
import com.example.ecommerceApp.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        return orderItemRepository.findById(id)
                .map(existing -> {
                    existing.setQuantity(orderItem.getQuantity());
                    existing.setOrder(orderItem.getOrder());
                    existing.setProduct(orderItem.getProduct());
                    return orderItemRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}

