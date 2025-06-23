package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Address;
import com.example.ecommerceApp.model.Order;
import com.example.ecommerceApp.repository.AddressRepository;
import com.example.ecommerceApp.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Address savedAddress;

    @BeforeEach
    public void setup() {
        orderRepository.deleteAll();
        addressRepository.deleteAll();

        // Adaugam o adresa valida in baza pentru test
        Address address = new Address();
        address.setStreet("Test Street 123");
        address.setCity("Test City");
        address.setPostalCode("12345");
        address.setCountry("Test Country");
        savedAddress = addressRepository.save(address);
    }

    @Test
    @Commit
    public void testCreateOrder_Success() throws Exception {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAddress(savedAddress);

        String orderJson = objectMapper.writeValueAsString(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.id", is(savedAddress.getId().intValue())));
    }

    @Test
    @Transactional
    @Commit
    public void testCreateAndGetOrder() throws Exception {
        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setPostalCode("12345");
        address.setCountry("Country");

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAddress(address);

        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(get("/api/orders/" + savedOrder.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedOrder.getId()))
                .andExpect(jsonPath("$.address.id").value(savedOrder.getAddress().getId()))
                .andExpect(jsonPath("$.address.street").value("Test Street"));
    }



    @Test
    @Commit
    public void testGetOrderById_NotFound() throws Exception {
        mockMvc.perform(get("/api/orders/9999"))
                .andExpect(status().isNotFound());
    }
}
