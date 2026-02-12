package com.example.Book_Store.controller;

import com.example.Book_Store.model.User;
import com.example.Book_Store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user-profile"; // We might need to create this template
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        // Handle other fields...
        userService.save(currentUser);
        return "redirect:/user/profile?success";
    }
}
