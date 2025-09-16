package com.yp.controller;

import com.yp.model.User;
import com.yp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists!");
            return "registration";
        }
        userService.addUser(new User(username, password));
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }
}
