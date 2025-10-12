package com.yogesh.service;

import com.yogesh.dao.SeatDAO;
import com.yogesh.dto.SeatDTO;
import com.yogesh.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatDAO seatDAO;

    public List<SeatDTO> fetchSeatLayoutAndAvailability(int showId) {
        List<Seat> seats = seatDAO.fetchSeatLayoutAndAvailability(showId);
        return seats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean reserveSeatTemporarily(int seatId, int showId) {
        return seatDAO.reserveSeatTemporarily(seatId, showId);
    }

    private SeatDTO convertToDTO(Seat seat) {
        SeatDTO dto = new SeatDTO();
        dto.setSeatId(seat.getSeatId());
        dto.setShowId(seat.getShowId());
        dto.setSeatNumber(seat.getSeatNumber());
        dto.setSeatType(seat.getSeatType());
        dto.setPrice(seat.getPrice());
        dto.setAvailable(seat.isAvailable());
        dto.setHandicapped(seat.isHandicapped());
        return dto;
    }
}
