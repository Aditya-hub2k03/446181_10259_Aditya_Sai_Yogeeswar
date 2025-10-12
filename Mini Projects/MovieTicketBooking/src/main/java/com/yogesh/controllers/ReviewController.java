package com.yogesh.controllers;

import com.yogesh.dto.ReviewDTO;
import com.yogesh.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/list")
    public String reviewList(@RequestParam(name = "movieId") int movieId, Model model) {
        List<ReviewDTO> reviews = reviewService.fetchMovieReviews(movieId);
        model.addAttribute("reviews", reviews);
        model.addAttribute("movieId", movieId);
        return "review-list";
    }

    @PostMapping("/add")
    public String addReview(
            @SessionAttribute(name = "userId") int userId,
            @RequestParam(name = "movieId") int movieId,
            @RequestParam(name = "rating") double rating,
            @RequestParam(name = "comment") String comment) {
        boolean success = reviewService.addMovieReview(movieId, userId, rating, comment);
        if (success) {
            return "redirect:/MovieTicketBooking/review/list?movieId=" + movieId + "&reviewSuccess";
        } else {
            return "redirect:/MovieTicketBooking/review/list?movieId=" + movieId + "&reviewError";
        }
    }
}
