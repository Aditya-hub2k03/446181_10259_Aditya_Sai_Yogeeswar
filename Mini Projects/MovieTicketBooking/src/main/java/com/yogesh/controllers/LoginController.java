package com.yogesh.controllers;

import com.yogesh.dto.UserDTO;
import com.yogesh.model.User;
import com.yogesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Display the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Handle login submission
    @PostMapping("/login")
    public String login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            HttpSession session,
            Model model) {

        // Fetch user from the database using UserService
        User user = userService.authenticate(email, password);

        if (user != null) {
            // Set user session attributes
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getUserName());
            return "redirect:" + "/home/loggedin";
        } else {
            // Add error message to the model
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
