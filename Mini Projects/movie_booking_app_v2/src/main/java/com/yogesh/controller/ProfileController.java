package com.yogesh.controller;

import com.yogesh.model.Booking;
import com.yogesh.model.User;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);

        List<Booking> bookings = userService.getPreviousBookings(user.getUserId());
        model.addAttribute("bookings", bookings);

        return "profile";
    }
}
