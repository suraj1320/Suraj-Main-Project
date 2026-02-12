package com.example.Book_Store.controller;

import com.example.Book_Store.model.Cart;
import com.example.Book_Store.model.User;
import com.example.Book_Store.service.CartService;
import com.example.Book_Store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        Cart cart = cartService.getCart(user);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId, @RequestParam(defaultValue = "1") int quantity,
            Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        cartService.addToCart(user, bookId, quantity);
        return "redirect:/shop"; // Or back to where they came from
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long cartItemId, @RequestParam int quantity, Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        cartService.updateCartItem(user, cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId, Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        cartService.removeFromCart(user, cartItemId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        cartService.clearCart(user);
        return "redirect:/cart";
    }
}
