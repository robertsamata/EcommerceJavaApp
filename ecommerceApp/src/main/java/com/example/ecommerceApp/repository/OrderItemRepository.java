package com.example.ecommerceApp.repository;

import com.example.ecommerceApp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
