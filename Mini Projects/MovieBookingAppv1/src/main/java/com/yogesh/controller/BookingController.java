package com.yogesh.controller;

import com.yogesh.dto.ApiResponse;
import com.yogesh.dto.BookingDto;
import com.yogesh.exception.BookingException;
import com.yogesh.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDto.BookingResponse>> bookSeats(@RequestBody BookingDto.BookingRequest request) {
        try {
            BookingDto.BookingResponse response = bookingService.bookSeats(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Booking successful", response));
        } catch (Exception e) {
            throw new BookingException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDto.BookingResponse>> getBooking(@PathVariable Long id) {
        try {
            BookingDto.BookingResponse response = bookingService.getBookingById(id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Booking not found", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Booking retrieved successfully", response));
        } catch (Exception e) {
            throw new BookingException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Booking cancelled successfully", "Cancelled"));
        } catch (Exception e) {
            throw new BookingException(e.getMessage());
        }
    }
}
