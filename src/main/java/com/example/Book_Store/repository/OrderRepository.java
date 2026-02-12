package com.example.Book_Store.repository;

import com.example.Book_Store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    java.util.List<Order> findByUser(com.example.Book_Store.model.User user);
}
