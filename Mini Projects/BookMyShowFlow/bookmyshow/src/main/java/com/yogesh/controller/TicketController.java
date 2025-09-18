package com.yogesh.controller;

import com.yogesh.dto.*;
import com.yogesh.exception.UserAlreadyExistsException;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectToSignup() {
        return "redirect:/signup";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new SignupUserRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute("user") SignupUserRequestDto request, Model model) throws ClassNotFoundException {
        try {
            SignupUserResponseDto response = userService.signupUser(request);
            model.addAttribute("message", "Signup successful! Please login.");
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") LoginRequestDto request, Model model, HttpSession session) throws ClassNotFoundException {
        try {
            LoginResponseDto response = userService.login(request, session);
            if (response.isLoggedIn()) {
                return "redirect:/welcome";
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage(Model model, HttpSession session) {
        if (!userService.isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        String userName = userService.getCurrentUserName(session);
        model.addAttribute("name", userName);
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "redirect:/login";
    }

    // Add this method to handle the welcome page from ratings context
    @GetMapping("/ratings/welcome")
    public String redirectToWelcome() {
        return "redirect:/welcome";
    }
}
