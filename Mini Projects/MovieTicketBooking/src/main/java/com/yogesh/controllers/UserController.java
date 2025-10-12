package com.yogesh.controllers;

import com.yogesh.dto.UserDTO;
import com.yogesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(@SessionAttribute(name = "userId") int userId, Model model) {
        UserDTO user = userService.fetchUserInformation(userId);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UserDTO userDTO, HttpSession session) {
        boolean success = userService.updateProfileInformation(userDTO);
        if (success) {
            session.setAttribute("userName", userDTO.getUserName());
            return "redirect:/MovieTicketBooking/user/profile?success";
        } else {
            return "redirect:/MovieTicketBooking/user/profile?error";
        }
    }

    @PostMapping("/add-payment-method")
    public String addPaymentMethod(
            @SessionAttribute(name = "userId") int userId,
            @RequestParam(name = "cardNumber") String cardNumber,
            @RequestParam(name = "cardHolder") String cardHolder,
            @RequestParam(name = "expiryDate") String expiryDate,
            @RequestParam(name = "paymentType") String paymentType) {
        boolean success = userService.addPaymentMethod(userId, cardNumber, cardHolder, expiryDate, paymentType);
        if (success) {
            return "redirect:/MovieTicketBooking/user/profile?paymentSuccess";
        } else {
            return "redirect:/MovieTicketBooking/user/profile?paymentError";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/MovieTicketBooking";
    }
}
