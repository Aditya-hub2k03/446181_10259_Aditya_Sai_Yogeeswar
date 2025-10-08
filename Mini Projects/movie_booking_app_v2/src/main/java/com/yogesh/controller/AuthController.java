package com.yogesh.controller;

import com.yogesh.dto.LoginRequestDto;
import com.yogesh.dto.LoginResponseDto;
import com.yogesh.dto.SignupRequestDto;
import com.yogesh.dto.SignupResponseDto;
import com.yogesh.model.User;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto dto, Model model) {
        SignupResponseDto response = userService.registerUser(dto);
        model.addAttribute("message", response.getMessage());
        return response.isSuccess() ? "login" : "signup";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto dto, HttpSession session, Model model) {
        LoginResponseDto response = userService.loginUser(dto);
        if (response.isSuccess()) {
            User user = userService.getUserById(response.getUserId());
            session.setAttribute("loggedInUser", user);
            return "redirect:/home_loggedin";
        } else {
            model.addAttribute("message", response.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
