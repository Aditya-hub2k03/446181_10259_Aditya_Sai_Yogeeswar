package com.yp.controller;

import com.yp.model.User;
import com.yp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials!");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        model.addAttribute("users", userService.getAllUsers());
        return "welcome";
    }
}
