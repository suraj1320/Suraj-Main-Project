package com.example.Book_Store.controller;

import com.example.Book_Store.model.Address;
import com.example.Book_Store.model.OrderItem;
import com.example.Book_Store.model.User;
import com.example.Book_Store.service.AddressService;
import com.example.Book_Store.service.OrderService;
import com.example.Book_Store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/address")
    public String selectAddressPage(Principal principal, Model model) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());
        List<Address> addresses = addressService.getAddressesByUser(user);
        model.addAttribute("addresses", addresses);
        model.addAttribute("user", user);

        List<String> states = java.util.Arrays.asList(
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
                "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra",
                "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab", "Rajasthan", "Sikkim",
                "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal",
                "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu",
                "Lakshadweep", "Delhi", "Puducherry", "Ladakh", "Jammu and Kashmir");
        model.addAttribute("states", states);

        return "checkout-address";
    }

    @PostMapping("/address/add")
    public String addAddress(@RequestParam String street,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zipCode,
            Principal principal) {
        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        address.setUser(user);

        addressService.saveAddress(address);

        return "redirect:/checkout/address";
    }

    @PostMapping("/summary")
    public String checkoutSummary(@RequestParam Long addressId, Principal principal, Model model) {
        if (principal == null)
            return "redirect:/login";

        Address address = addressService.getAddressById(addressId);
        if (address == null || !address.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/checkout/address";
        }

        model.addAttribute("address", address);
        return "checkout-summary";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam("cartJson") String cartJson,
            @RequestParam Long addressId,
            Principal principal,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        if (principal == null)
            return "redirect:/login";
        User user = userService.findByUsername(principal.getName());

        try {
            Address address = addressService.getAddressById(addressId);
            if (address == null || !address.getUser().getUsername().equals(principal.getName())) {
                redirectAttributes.addFlashAttribute("error", "Invalid Address.");
                return "redirect:/checkout/address";
            }

            // Manually parse JSON to list of maps to avoid strict mapping issues
            List<java.util.Map<String, Object>> cartItems = objectMapper.readValue(cartJson,
                    new com.fasterxml.jackson.core.type.TypeReference<List<java.util.Map<String, Object>>>() {
                    });

            if (cartItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Cart is empty.");
                return "redirect:/shop";
            }

            List<OrderItem> items = new java.util.ArrayList<>();
            for (java.util.Map<String, Object> map : cartItems) {
                // Determine ID (handle string or number)
                Object idObj = map.get("id");
                Long bookId = Long.valueOf(idObj.toString());
                int quantity = Integer.parseInt(map.get("quantity").toString());

                com.example.Book_Store.model.Book book = new com.example.Book_Store.model.Book();
                book.setId(bookId);

                OrderItem item = new OrderItem();
                item.setBook(book);
                item.setQuantity(quantity);
                items.add(item);
            }

            String fullAddress = address.getStreet() + ", " + address.getCity() + ", " + address.getState() + " "
                    + address.getZipCode();
            String recipientName = user.getFirstName() + " " + user.getLastName();
            if (recipientName == null || recipientName.trim().isEmpty())
                recipientName = user.getUsername();

            orderService.placeOrder(user, items, recipientName, user.getPhoneNumber(), fullAddress);

            return "redirect:/payment";

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Checkout Error: " + e.getMessage());
            String errorMessage = "Order Failed: " + e.getMessage();
            if (e.getMessage().contains("Book details not found")) {
                errorMessage = "Your cart contains items that are no longer available. Please clear your cart and try again.";
            } else if (e.getMessage().contains("Insufficient stock")) {
                errorMessage = "Some items in your cart are out of stock.";
            }
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/shop";
        }
    }
}
