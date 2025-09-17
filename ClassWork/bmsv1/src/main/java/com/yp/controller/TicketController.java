package com.yp.controller;

import com.yp.dto.*;
import com.yp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TicketController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupUserRequestDto requestDto, Model model) {
        SignupUserResponseDto response = userService.signupUser(requestDto);
        model.addAttribute("response", response);
        return "signup";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginRequestDto requestDto, Model model) {
        LoginResponseDto response = userService.login(requestDto);
        model.addAttribute("response", response);
        if (response.isLoggedIn()) {
            return "redirect:/welcome";
        }
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
