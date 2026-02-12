package com.example.Book_Store.controller;

import com.example.Book_Store.model.enums.Category;
import com.example.Book_Store.model.Book;
import com.example.Book_Store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private com.example.Book_Store.service.BookService bookService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("bookCount", bookService.count());
        return "admin";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Category.values());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "add-book";
        }
        bookService.saveBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/edit-book/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", Category.values());
        return "add-book"; // Reusing the add-book form
    }

    @PostMapping("/update-book/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute Book book, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            book.setId(id); // Keep ID so form submits to correct URL
            return "add-book";
        }
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin";
    }
}
