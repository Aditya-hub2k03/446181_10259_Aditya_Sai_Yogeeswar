package com.yogesh.controller;

import com.yogesh.model.*;
import com.yogesh.service.*;
import com.yogesh.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ShowService showService;

    @GetMapping("/show/{showId}")
    public String showBookingPage(@PathVariable int showId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";

        List<Seat> seats = seatService.getSeatsByShow(showId);
        Show show = showService.getShowById(showId);

        model.addAttribute("seats", seats);
        model.addAttribute("show", show);
        model.addAttribute("showId", showId);
        return "booking";
    }

    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam int showId,
                                 @RequestParam String selectedSeats,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if(user == null) return "redirect:/login";

        BookingRequestDto dto = new BookingRequestDto();
        dto.setShowId(showId);
        dto.setUserId(user.getUserId());
        dto.setSelectedSeats(selectedSeats);

        BookingResponseDto response = bookingService.createBooking(dto);

        model.addAttribute("bookingResponse", response);
        model.addAttribute("selectedSeats", selectedSeats);
        model.addAttribute("showId", showId);

        return "booking_confirmation";
    }
}
