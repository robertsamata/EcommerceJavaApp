package com.example.ecommerceApp.service;

import com.example.ecommerceApp.model.Address;
import com.example.ecommerceApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        return addressRepository.findById(id)
                .map(existing -> {
                    existing.setStreet(address.getStreet());
                    existing.setCity(address.getCity());
                    existing.setPostalCode(address.getPostalCode());
                    existing.setCountry(address.getCountry());
                    return addressRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
