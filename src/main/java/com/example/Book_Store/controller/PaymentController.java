package com.example.Book_Store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping
    public String paymentPage() {
        return "payment";
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam("method") String method) {
        if ("cod".equals(method)) {
            return "redirect:/order-success?payment=cod";
        }
        return "redirect:/order-success";
    }
}
