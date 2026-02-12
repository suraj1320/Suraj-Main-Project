package com.example.Book_Store.service;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.model.Order;
import com.example.Book_Store.model.OrderItem;
import com.example.Book_Store.model.User;
import com.example.Book_Store.repository.BookRepository;
import com.example.Book_Store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void placeOrder(User user, List<OrderItem> items, String recipientName, String phoneNumber,
            String shippingAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setRecipientName(recipientName);
        order.setPhoneNumber(phoneNumber);
        order.setShippingAddress(shippingAddress);
        order.setStatus("Pending");

        double totalAmount = 0.0;

        // Items logic
        // Note: The items passed in might be transient objects from JSON mappping.
        // We need to create persisted OrderItem linked to Order.

        for (OrderItem itemReq : items) {
            Book book = bookRepository.findById(itemReq.getBook().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Book details not found for ID: " + itemReq.getBook().getId()));

            if (book.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }

            // Deduct stock
            book.setStock(book.getStock() - itemReq.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(book.getPrice());

            // Link to order
            order.addItem(orderItem);

            totalAmount += book.getPrice() * itemReq.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
