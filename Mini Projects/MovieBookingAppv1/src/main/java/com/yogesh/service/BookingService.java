package com.yogesh.service;

import com.yogesh.dto.BookingDto;

public interface BookingService {
    BookingDto.BookingResponse bookSeats(BookingDto.BookingRequest request) throws Exception;
    BookingDto.BookingResponse getBookingById(Long bookingId) throws Exception;
    void cancelBooking(Long bookingId) throws Exception;
}
