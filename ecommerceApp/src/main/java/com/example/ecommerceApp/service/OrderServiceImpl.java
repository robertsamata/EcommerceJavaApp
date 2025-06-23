package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Address;
import com.example.ecommerceApp.model.Order;
import com.example.ecommerceApp.model.OrderItem;
import com.example.ecommerceApp.repository.AddressRepository;
import com.example.ecommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Order createOrder(Order order) {
        // Verifică dacă Address are ID, încarcă-l din DB
        if (order.getAddress() != null && order.getAddress().getId() != null) {
            Address existingAddress = addressRepository.findById(order.getAddress().getId())
                    .orElseThrow(() -> new RuntimeException("Address not found with id: " + order.getAddress().getId()));
            order.setAddress(existingAddress);
        } else {
            throw new RuntimeException("Order must have an existing address with id");
        }

        // Configurează relația inversă pentru OrderItems, dacă există
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            Set<OrderItem> managedOrderItems = new HashSet<>();
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order); // setează relația inversă
                managedOrderItems.add(item);
            }
            order.setOrderItems(managedOrderItems);
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        return orderRepository.findById(id)
                .map(existing -> {
                    existing.setOrderDate(order.getOrderDate());

                    if (order.getAddress() != null && order.getAddress().getId() != null) {
                        Address existingAddress = addressRepository.findById(order.getAddress().getId())
                                .orElseThrow(() -> new RuntimeException("Address not found with id: " + order.getAddress().getId()));
                        existing.setAddress(existingAddress);
                    }

                    if (order.getOrderItems() != null) {
                        Set<OrderItem> managedOrderItems = new HashSet<>();
                        for (OrderItem item : order.getOrderItems()) {
                            item.setOrder(existing);
                            managedOrderItems.add(item);
                        }
                        existing.setOrderItems(managedOrderItems);
                    }

                    return orderRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
