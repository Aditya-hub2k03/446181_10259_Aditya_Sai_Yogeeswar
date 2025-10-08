package com.yogesh.controller;

import com.yogesh.model.Show;
import com.yogesh.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private ShowService showService;

    @GetMapping("/list/{movieId}")
    public String listTheatres(@PathVariable int movieId, Model model) {
        List<Show> shows = showService.getShowsByMovie(movieId);
        model.addAttribute("shows", shows);
        model.addAttribute("movieId", movieId);
        return "theatre_list";
    }
}
