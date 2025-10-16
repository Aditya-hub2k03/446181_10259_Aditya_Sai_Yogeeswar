package com.yogesh.controllers;

import com.yogesh.dto.PaymentDTO;
import com.yogesh.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public String processPayment(
            @RequestParam(name = "bookingId") int bookingId,
            @RequestParam(name = "userId") int userId,
            @RequestParam(name = "amount") double amount,
            @RequestParam(name = "paymentMethod") String paymentMethod,
            @RequestParam(name = "transactionId") String transactionId) {
        boolean success = paymentService.insertPaymentRecord(bookingId, userId, amount, paymentMethod, "SUCCESS", transactionId);
        if (success) {
            return "redirect:/MovieTicketBooking/booking/confirmation?bookingId=" + bookingId + "&status=CONFIRMED";
        } else {
            return "redirect:/MovieTicketBooking/booking/payment?bookingId=" + bookingId + "&paymentError";
        }
    }

    @PostMapping("/details")
    public String paymentDetails(@RequestParam(name = "bookingId") int bookingId, Model model) {
        PaymentDTO payment = paymentService.fetchPaymentDetails(bookingId);
        model.addAttribute("payment", payment);
        return "payment-details";
    }
}
