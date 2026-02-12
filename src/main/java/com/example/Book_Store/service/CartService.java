package com.example.Book_Store.service;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.model.Cart;
import com.example.Book_Store.model.CartItem;
import com.example.Book_Store.model.User;
import com.example.Book_Store.repository.BookRepository;
import com.example.Book_Store.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    public Cart getCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    @Transactional
    public void addToCart(User user, Long bookId, int quantity) {
        Cart cart = getCart(user);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(book, quantity);
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void updateCartItem(User user, Long cartItemId, int quantity) {
        Cart cart = getCart(user);
        Optional<CartItem> itemOpt = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst();

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (quantity <= 0) {
                cart.removeItem(item);
            } else {
                item.setQuantity(quantity);
            }
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void removeFromCart(User user, Long cartItemId) {
        Cart cart = getCart(user);
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = getCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
