package com.example.Book_Store.controller;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/shop")
    public String shop(Model model,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) com.example.Book_Store.model.enums.Category category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books;

        if (keyword != null && !keyword.isEmpty()) {
            books = bookRepository.search(keyword, pageable);
        } else if (category != null) {
            books = bookRepository.findByCategory(category, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        model.addAttribute("books", books);
        model.addAttribute("categories", com.example.Book_Store.model.enums.Category.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "shop";
    }

    @org.springframework.web.bind.annotation.GetMapping("/about")
    public String about() {
        return "about";
    }

}
