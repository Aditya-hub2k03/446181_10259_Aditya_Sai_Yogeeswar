package com.yogesh.controllers;

import com.yogesh.dto.ShowDTO;
import com.yogesh.dto.TheatreAdminDTO;
import com.yogesh.model.Role;
import com.yogesh.model.Show;
import com.yogesh.model.User;
import com.yogesh.service.MovieService;
import com.yogesh.service.RoleService;
import com.yogesh.service.ShowService;
import com.yogesh.service.TheatreAdminService;
import com.yogesh.service.UserService;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/theatre-admin")
public class TheatreAdminController {

    @Autowired
    private TheatreAdminService theatreAdminService;

    @Autowired
    private ShowService showService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/dashboard")
    public String theatreAdminDashboard(@SessionAttribute("userId") int userId, Model model, HttpSession session) {
        // Check if the user is a theatre admin
        boolean isTheatreAdmin = roleService.hasRole(userId, "THEATRE_ADMIN");
        if (!isTheatreAdmin) {
            return "redirect:/home/common";
        }

        // Get theatres managed by this admin
        List<TheatreAdminDTO> theatres = theatreAdminService.getTheatresByAdminId(userId);
        model.addAttribute("theatres", theatres);

        // Store theatre admin status in session
        session.setAttribute("isTheatreAdmin", true);

        return "theatre-admin-dashboard";
    }

    @GetMapping("/manage-shows")
    public String manageShows(
            @SessionAttribute("userId") int userId,
            @RequestParam("theatreId") int theatreId,
            Model model, HttpSession session) {

        // Check if the user is a theatre admin
        boolean isTheatreAdmin = roleService.hasRole(userId, "THEATRE_ADMIN");
        if (!isTheatreAdmin) {
            return "redirect:/home/common";
        }

        // Verify this admin manages the requested theatre
        List<TheatreAdminDTO> theatres = theatreAdminService.getTheatresByAdminId(userId);
        if (theatres.stream().noneMatch(ta -> ta.getTheatreId() == theatreId)) {
            return "redirect:/theatre-admin/dashboard";
        }

        List<ShowDTO> shows = showService.getShowsByTheatreId(theatreId);
        List<com.yogesh.dto.MovieDTO> movies = movieService.getAllMovies();
        model.addAttribute("shows", shows);
        model.addAttribute("movies", movies);
        model.addAttribute("theatreId", theatreId);
        return "manage-shows";
    }

    @PostMapping("/add-show")
    public String addShow(
            @SessionAttribute("userId") int userId,
            @RequestParam("theatreId") int theatreId,
            @RequestParam("movieId") int movieId,
            @RequestParam("showDate") String showDate,
            @RequestParam("showTime") String showTime,
            @RequestParam("format") String format,
            HttpSession session) {

        // Check if the user is a theatre admin
        boolean isTheatreAdmin = roleService.hasRole(userId, "THEATRE_ADMIN");
        if (!isTheatreAdmin) {
            return "redirect:/home/common";
        }

        // Verify this admin manages the requested theatre
        List<TheatreAdminDTO> theatres = theatreAdminService.getTheatresByAdminId(userId);
        if (theatres.stream().noneMatch(ta -> ta.getTheatreId() == theatreId)) {
            session.setAttribute("error", "You don't have permission to manage this theatre");
            return "redirect:/theatre-admin/dashboard";
        }

        showService.addShow(theatreId, movieId, showDate, showTime, format);
        return "redirect:/theatre-admin/manage-shows?theatreId=" + theatreId;
    }

    @PostMapping("/update-show")
    public String updateShow(
            @SessionAttribute("userId") int userId,
            @RequestParam("showId") int showId,
            @RequestParam("showDate") String showDate,
            @RequestParam("showTime") String showTime,
            @RequestParam("format") String format,
            HttpSession session) {

        Show show = showService.getShowById(showId);

        // Check if the user is a theatre admin
        boolean isTheatreAdmin = roleService.hasRole(userId, "THEATRE_ADMIN");
        if (!isTheatreAdmin) {
            return "redirect:/home/common";
        }

        // Verify this admin manages the show's theatre
        List<TheatreAdminDTO> theatres = theatreAdminService.getTheatresByAdminId(userId);
        if (theatres.stream().noneMatch(ta -> ta.getTheatreId() == show.getTheatreId())) {
            session.setAttribute("error", "You don't have permission to manage this show");
            return "redirect:/theatre-admin/dashboard";
        }

        showService.updateShow(showId, showDate, showTime, format);
        return "redirect:/theatre-admin/manage-shows?theatreId=" + show.getTheatreId();
    }
}
