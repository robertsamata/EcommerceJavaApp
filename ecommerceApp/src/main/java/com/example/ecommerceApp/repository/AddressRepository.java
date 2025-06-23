package com.example.ecommerceApp.repository;

import com.example.ecommerceApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
