package com.yogesh.controller;

import com.yogesh.model.Booking;
import com.yogesh.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/start")
    public String startPayment(HttpSession session, Model model) {
        Booking booking = (Booking) session.getAttribute("currentBooking");
        if (booking == null) return "redirect:/";
        model.addAttribute("booking", booking);
        return "payment";
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam String method,
                                 HttpSession session,
                                 Model model) {
        Booking booking = (Booking) session.getAttribute("currentBooking");
        if (booking == null) return "redirect:/";

        boolean success = paymentService.processPayment(booking.getBookingId(), method, booking.getTotalAmount());
        if (success) {
            model.addAttribute("status", "success");
            return "confirmation";
        } else {
            model.addAttribute("status", "failure");
            return "confirmation";
        }
    }
}
