package com.yogesh.controllers;

import com.yogesh.dto.BookmarkDTO;
import com.yogesh.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/list")
    public String bookmarkList(@SessionAttribute(name = "userId") int userId, Model model) {
        List<BookmarkDTO> bookmarks = bookmarkService.fetchBookmarkedMovies(userId);
        model.addAttribute("bookmarks", bookmarks);
        return "bookmark-list";
    }

    @PostMapping("/add")
    public String addBookmark(
            @SessionAttribute(name = "userId") int userId,
            @RequestParam(name = "movieId") int movieId) {
        boolean success = bookmarkService.addBookmark(userId, movieId);
        if (success) {
            return "redirect:/MovieTicketBooking/bookmark/list?bookmarkSuccess";
        } else {
            return "redirect:/MovieTicketBooking/bookmark/list?bookmarkError";
        }
    }
}
