package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Address;
import com.example.ecommerceApp.model.Order;
import com.example.ecommerceApp.repository.AddressRepository;
import com.example.ecommerceApp.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ActiveProfiles("test")
@SpringBootTest
public class OrderServiceUnitTest {

    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        addressRepository = Mockito.mock(AddressRepository.class);
        orderService = new OrderServiceImpl(orderRepository, addressRepository);
    }

    @Test
    @Commit
    void testCreateOrder_Success() { // testam crearea unei comenzi
        Address address = new Address();
        address.setId(1L);

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAddress(address);

        when(addressRepository.existsById(1L)).thenReturn(true);
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.createOrder(order);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getAddress());
        assertEquals(1L, savedOrder.getAddress().getId());
    }

    @Test
    @Commit
    void testGetOrderById_Found() { //testam cautarea unei comenzi
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @Commit
    void testGetOrderById_NotFound() { //cautarea unei comenzi cand nu exista
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(2L);

        assertFalse(result.isPresent());
    }
}
