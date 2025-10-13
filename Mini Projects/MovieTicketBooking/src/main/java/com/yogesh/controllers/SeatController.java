package com.yogesh.controllers;

import com.yogesh.dto.SeatDTO;
import com.yogesh.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/layout")
    public String seatLayout(@RequestParam(name = "showId") int showId, Model model) {
        List<SeatDTO> seats = seatService.fetchSeatLayoutAndAvailability(showId);
        model.addAttribute("seats", seats);
        model.addAttribute("showId", showId);
        return "seat-layout";
    }

    @PostMapping("/reserve")
    public String reserveSeat(
            @RequestParam(name = "showId") int showId,
            @RequestParam(name = "seatIds") String seatIds) {
        if (seatIds == null || seatIds.isEmpty()) {
            return "redirect:/seat/layout?showId=" + showId + "&error=No seats selected";
        }

        String[] seatIdArray = seatIds.split(",");
        boolean success = true;

        for (String seatIdStr : seatIdArray) {
            try {
                int seatId = Integer.parseInt(seatIdStr);
                if (!seatService.reserveSeatTemporarily(seatId, showId)) {
                    success = false;
                    break;
                }
            } catch (NumberFormatException e) {
                success = false;
                break;
            }
        }

        if (success) {
            return "redirect:/seat/layout?showId=" + showId + "&reserveSuccess";
        } else {
            return "redirect:/seat/layout?showId=" + showId + "&reserveError";
        }
    }
}
