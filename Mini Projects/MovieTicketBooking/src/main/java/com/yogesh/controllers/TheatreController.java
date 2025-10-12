package com.yogesh.controllers;

import com.yogesh.dto.TheatreDTO;
import com.yogesh.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/list")
    public String theatreList(@RequestParam(name = "movieId") int movieId, Model model) {
        List<TheatreDTO> theatres = theatreService.fetchTheatresAndShowTimingsForMovie(movieId);
        List<String> dates = theatreService.fetchAvailableDatesForMovie(movieId);
        model.addAttribute("theatres", theatres);
        model.addAttribute("dates", dates);
        model.addAttribute("movieId", movieId);
        return "theatre-list";
    }
}
