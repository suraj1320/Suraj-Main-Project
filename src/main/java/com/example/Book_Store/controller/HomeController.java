package com.example.Book_Store.controller;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Fetch specific categories or random books for "Popular" section if needed
        // For now, let's just pass some dummy interaction data or recent books
        List<Book> books = bookRepository.findAll();
        // Limit to top 5 for hero slider or featured if we had logic,
        // but for now passing all to let frontend decide what to show or just empty if
        // new
        model.addAttribute("books", books);
        return "home";
    }

    @org.springframework.web.bind.annotation.GetMapping("/shop")
    public String shop(Model model,
            @org.springframework.web.bind.annotation.RequestParam(value = "keyword", required = false) String keyword,
            @org.springframework.web.bind.annotation.RequestParam(value = "category", required = false) com.example.Book_Store.model.enums.Category category) {
        List<Book> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.search(keyword);
        } else if (category != null) {
            books = bookRepository.findByCategory(category);
        } else {
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        model.addAttribute("categories", com.example.Book_Store.model.enums.Category.values());
        return "shop";
    }

    @org.springframework.web.bind.annotation.GetMapping("/about")
    public String about() {
        return "about";
    }

    @org.springframework.web.bind.annotation.GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
