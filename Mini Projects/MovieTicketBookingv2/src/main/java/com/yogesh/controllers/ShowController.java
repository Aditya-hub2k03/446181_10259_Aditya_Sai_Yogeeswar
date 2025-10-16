package com.yogesh.controllers;

import com.yogesh.dto.ShowDTO;
import com.yogesh.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/details")
    public String showDetails(@RequestParam(name = "showId") int showId, Model model) {
        ShowDTO show = showService.fetchShowDetails(showId);
        model.addAttribute("show", show);
        return "show-details";
    }

    @GetMapping("/list")
    public String showList(@RequestParam(name = "theatreId") int theatreId, @RequestParam(name = "movieId") int movieId, Model model) {
        List<ShowDTO> shows = showService.fetchShowsByTheatreAndMovie(theatreId, movieId);
        model.addAttribute("shows", shows);
        model.addAttribute("theatreId", theatreId);
        model.addAttribute("movieId", movieId);
        return "show-list";
    }
}
