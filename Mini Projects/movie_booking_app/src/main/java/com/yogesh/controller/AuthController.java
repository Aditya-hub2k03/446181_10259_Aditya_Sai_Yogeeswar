package com.yogesh.controller;

import com.yogesh.model.User;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        User user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/";
        }
        model.addAttribute("error", "Invalid email or password!");
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String city,
                               Model model) {
        boolean created = userService.registerUser(name, email, password, city);
        if (created) {
            model.addAttribute("msg", "Account created successfully! Please login.");
            return "login";
        }
        model.addAttribute("error", "Email already exists!");
        return "signup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
