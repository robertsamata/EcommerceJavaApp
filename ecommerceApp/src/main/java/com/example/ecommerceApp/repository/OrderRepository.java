package com.example.ecommerceApp.repository;

import com.example.ecommerceApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
