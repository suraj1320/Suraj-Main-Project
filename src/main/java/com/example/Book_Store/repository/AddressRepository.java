package com.example.Book_Store.repository;

import com.example.Book_Store.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(com.example.Book_Store.model.User user);
}
