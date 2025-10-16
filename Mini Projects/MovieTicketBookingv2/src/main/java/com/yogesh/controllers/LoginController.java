package com.yogesh.controllers;

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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            HttpSession session,
            Model model) {

        User user = userService.authenticate(email, password);

        if (user != null) {
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userName", user.getUserName());

            // Check user roles and redirect accordingly
            if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("APPLICATION_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("THEATRE_ADMIN"))) {
                return "redirect:/theatre-admin/dashboard";
            } else {
                return "redirect:/home/loggedin";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}
