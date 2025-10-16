package com.yogesh.service;

import com.yogesh.dao.BookingDAO;
import com.yogesh.dto.BookingDTO;
import com.yogesh.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    public int createBookingRecord(int userId, int showId, double totalPrice) {
        return bookingDAO.createBookingRecord(userId, showId, totalPrice);
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        return bookingDAO.updateBookingStatus(bookingId, status);
    }

    public BookingDTO fetchBookingDetails(int bookingId) {
        Booking booking = bookingDAO.fetchBookingDetails(bookingId);
        return convertToDTO(booking);
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUserId());
        dto.setShowId(booking.getShowId());
        dto.setBookingTime(booking.getBookingTime());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus());
        return dto;
    }
}
