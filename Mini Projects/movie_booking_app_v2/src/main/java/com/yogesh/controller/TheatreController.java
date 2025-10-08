package com.yogesh.controller;

import com.yogesh.model.Show;
import com.yogesh.model.Theatre;
import com.yogesh.service.ShowService;
import com.yogesh.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private ShowService showService;

    @GetMapping("/movie/{movieId}/theatres")
    public String theatreList(@PathVariable int movieId, Model model) {
        List<Show> shows = showService.getShowsByMovie(movieId);

        // Build a theatre map to use in the view (key = theatreId -> Theatre)
        Map<Integer, Theatre> theatreMap = new HashMap<>();
        for (Show s : shows) {
            int tId = s.getTheatreId();
            if (!theatreMap.containsKey(tId)) {
                Theatre t = theatreService.getTheatreById(tId);
                theatreMap.put(tId, t);
            }
        }

        model.addAttribute("shows", shows);
        model.addAttribute("theatreMap", theatreMap); // Use Theatre import in view
        return "theatre_list"; // JSP will read shows and theatreMap
    }

    @GetMapping("/theatre/{id}")
    public String showTheatreDetails(@PathVariable("id") int id, Model model) {
        Theatre theatre = theatreService.getTheatreById(id);
        model.addAttribute("theatre", theatre);
        return "theatre_details";
    }
}
