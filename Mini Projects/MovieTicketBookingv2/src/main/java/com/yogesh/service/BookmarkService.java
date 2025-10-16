package com.yogesh.service;

import com.yogesh.dao.BookmarkDAO;
import com.yogesh.dto.BookmarkDTO;
import com.yogesh.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkDAO bookmarkDAO;

    public List<BookmarkDTO> fetchBookmarkedMovies(int userId) {
        List<Bookmark> bookmarks = bookmarkDAO.fetchBookmarkedMovies(userId);
        return bookmarks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean addBookmark(int userId, int movieId) {
        return bookmarkDAO.addBookmark(userId, movieId);
    }

    private BookmarkDTO convertToDTO(Bookmark bookmark) {
        BookmarkDTO dto = new BookmarkDTO();
        dto.setBookmarkId(bookmark.getBookmarkId());
        dto.setUserId(bookmark.getUserId());
        dto.setMovieId(bookmark.getMovieId());
        dto.setCreatedOn(bookmark.getCreatedOn());
        return dto;
    }
}
