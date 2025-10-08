package com.yogesh.controller;

import com.yogesh.model.Booking;
import com.yogesh.model.User;
import com.yogesh.service.BookingService;
import com.yogesh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/auth/login";

        List<Booking> bookings = bookingService.getBookingsByUser(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String city,
                                HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            userService.updateUserProfile(user.getUserId(), name, city);
            user.setUserName(name);
            user.setCity(city);
            session.setAttribute("loggedUser", user);
        }
        return "redirect:/profile";
    }
}
