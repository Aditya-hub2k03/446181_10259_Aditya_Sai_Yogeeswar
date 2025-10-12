package com.yogesh.controllers;

import com.yogesh.model.User;
import com.yogesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Handle registration submission
    @PostMapping("/register")
    public String registerUser(
            @RequestParam(name = "userName") String userName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "preferredGenre") String preferredGenre,
            Model model) {

        // Check if the email is already registered
        if (userService.isEmailRegistered(email)) {
            model.addAttribute("error", "Email is already registered.");
            return "register";
        }

        // Create a new user
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setCity(city);
        user.setPreferredGenre(preferredGenre);

        // Save the user to the database
        boolean success = userService.registerUser(user);

        if (success) {
            model.addAttribute("success", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
    }
}
