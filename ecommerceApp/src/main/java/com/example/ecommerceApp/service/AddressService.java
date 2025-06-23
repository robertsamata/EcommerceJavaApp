package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address createAddress(Address address);
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(Long id);
    Address updateAddress(Long id, Address address);
    void deleteAddress(Long id);
}
