package com.example.Book_Store.controller;

import com.example.Book_Store.model.Order;
import com.example.Book_Store.model.OrderItem;
import com.example.Book_Store.model.User;
import com.example.Book_Store.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class OrderController {

    @Autowired
    private com.example.Book_Store.service.OrderService orderService;

    @Autowired
    private com.example.Book_Store.service.UserService userService;

    // Checkout logic moved to CheckoutController

    // Checkout logic moved to CheckoutController

    // Payment handled by PaymentController

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }

    @GetMapping("/my-orders")
    public String myOrders(java.security.Principal principal, Model model) {
        if (principal == null)
            return "redirect:/login";

        User user = userService.findByUsername(principal.getName());

        model.addAttribute("orders", orderService.getOrdersByUser(user));
        model.addAttribute("username", user.getUsername());

        return "orders";
    }
}
