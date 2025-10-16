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
    public List<ShowDTO> getShowsByTheatreId(int theatreId) {
        List<Show> shows = showDAO.getShowsByTheatreId(theatreId);
        return shows.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Show getShowById(int showId) {
        return showDAO.getShowById(showId);
    }

    public boolean addShow(int theatreId, int movieId, String showDate, String showTime, String format) {
        return showDAO.addShow(theatreId, movieId, showDate, showTime, format);
    }

    public boolean updateShow(int showId, String showDate, String showTime, String format) {
        return showDAO.updateShow(showId, showDate, showTime, format);
    }

    private ShowDTO convertToDTO(Show show) {
        ShowDTO dto = new ShowDTO();
        dto.setShowId(show.getShowId());
        dto.setTheatreId(show.getTheatreId());
        dto.setMovieId(show.getMovieId());
        dto.setShowDate(show.getShowDate());
        dto.setShowTime(show.getShowTime());
        dto.setFormat(show.getFormat());
        return dto;
    }

    private Show convertToEntity(ShowDTO showDTO) {
        Show show = new Show();
        show.setShowId(showDTO.getShowId());
        show.setTheatreId(showDTO.getTheatreId());
        show.setMovieId(showDTO.getMovieId());
        show.setShowDate(showDTO.getShowDate());
        show.setShowTime(showDTO.getShowTime());
        show.setFormat(showDTO.getFormat());
        return show;
    }
}
