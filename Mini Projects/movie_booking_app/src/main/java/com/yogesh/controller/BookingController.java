package com.yogesh.controller;

import com.yogesh.model.Booking;
import com.yogesh.model.User;
import com.yogesh.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/select/{showId}")
    public String selectSeats(@PathVariable int showId, Model model) {
        model.addAttribute("showId", showId);
        return "booking";
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam int showId,
                                 @RequestParam double total,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/auth/login";

        Booking booking = bookingService.createBooking(user.getUserId(), showId, total);
        session.setAttribute("currentBooking", booking);
        return "redirect:/payment/start";
    }
}
