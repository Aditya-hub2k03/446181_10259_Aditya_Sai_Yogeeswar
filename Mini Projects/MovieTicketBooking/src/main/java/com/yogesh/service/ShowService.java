package com.yogesh.service;

import com.yogesh.dao.ShowDAO;
import com.yogesh.dto.ShowDTO;
import com.yogesh.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private ShowDAO showDAO;

    public ShowDTO fetchShowDetails(int showId) {
        Show show = showDAO.fetchShowDetails(showId);
        return convertToDTO(show);
    }

    public List<ShowDTO> fetchShowsByTheatreAndMovie(int theatreId, int movieId) {
        List<Show> shows = showDAO.fetchShowsByTheatreAndMovie(theatreId, movieId);
        return shows.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ShowDTO convertToDTO(Show show) {
        ShowDTO dto = new ShowDTO();
        dto.setShowId(show.getShowId());
        dto.setMovieId(show.getMovieId());
        dto.setTheatreId(show.getTheatreId());
        dto.setShowDate(show.getShowDate());
        dto.setShowTime(show.getShowTime());
        dto.setFormat(show.getFormat());
        return dto;
    }
}
