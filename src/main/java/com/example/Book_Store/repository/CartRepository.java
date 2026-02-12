package com.example.Book_Store.repository;

import com.example.Book_Store.model.Cart;
import com.example.Book_Store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
