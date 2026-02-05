package com.example.Book_Store.controller;

import com.example.Book_Store.model.enums.Category;
import com.example.Book_Store.model.Book;
import com.example.Book_Store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("bookCount", bookRepository.count());
        return "admin";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Category.values());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book) {
        // Since we are skipping service layer, saving directly
        // Image handling would typically go here (upload to folder),
        // but for simplicity we might just expect a URL or handle basic logic later if
        // requested
        bookRepository.save(book);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/admin";
    }
}
