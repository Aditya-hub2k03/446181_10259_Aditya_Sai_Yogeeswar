package com.yogesh.controllers;

import com.yogesh.dto.BookingDTO;
import com.yogesh.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public String createBooking(
            @SessionAttribute(name = "userId") int userId,
            @RequestParam(name = "showId") int showId,
            @RequestParam(name = "totalPrice") double totalPrice) {
        int bookingId = bookingService.createBookingRecord(userId, showId, totalPrice);
        return "redirect:/MovieTicketBooking/booking/payment?bookingId=" + bookingId;
    }

    @GetMapping("/payment")
    public String bookingPayment(@RequestParam(name = "bookingId") int bookingId, Model model) {
        BookingDTO booking = bookingService.fetchBookingDetails(bookingId);
        model.addAttribute("booking", booking);
        return "booking-payment";
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam(name = "bookingId") int bookingId, @RequestParam(name = "status") String status) {
        boolean success = bookingService.updateBookingStatus(bookingId, status);
        if (success) {
            return "redirect:/MovieTicketBooking/booking/confirmation?bookingId=" + bookingId + "&status=" + status;
        } else {
            return "redirect:/MovieTicketBooking/booking/payment?bookingId=" + bookingId + "&error";
        }
    }

    @GetMapping("/confirmation")
    public String bookingConfirmation(@RequestParam(name = "bookingId") int bookingId, @RequestParam(name = "status") String status, Model model) {
        BookingDTO booking = bookingService.fetchBookingDetails(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("status", status);
        return "booking-confirmation";
    }
}
