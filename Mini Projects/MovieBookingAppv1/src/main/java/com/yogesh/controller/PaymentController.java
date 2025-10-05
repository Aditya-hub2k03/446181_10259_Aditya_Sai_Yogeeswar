package com.yogesh.controller;

import com.yogesh.dto.ApiResponse;
import com.yogesh.dto.PaymentDto;
import com.yogesh.exception.PaymentException;
import com.yogesh.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDto.PaymentResponse>> makePayment(@RequestBody PaymentDto.PaymentRequest request) {
        try {
            PaymentDto.PaymentResponse response = paymentService.makePayment(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Payment successful", response));
        } catch (Exception e) {
            throw new PaymentException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDto.PaymentResponse>> getPayment(@PathVariable Long id) {
        try {
            PaymentDto.PaymentResponse response = paymentService.getPaymentById(id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Payment not found", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Payment retrieved successfully", response));
        } catch (Exception e) {
            throw new PaymentException(e.getMessage());
        }
    }
}
