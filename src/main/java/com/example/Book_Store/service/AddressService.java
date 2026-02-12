package com.example.Book_Store.service;

import com.example.Book_Store.model.Address;
import com.example.Book_Store.model.User;
import com.example.Book_Store.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUser(User user) {
        return addressRepository.findByUser(user);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
