package com.yogesh.service;

import com.yogesh.dao.TheatreDAO;
import com.yogesh.dto.TheatreDTO;
import com.yogesh.model.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheatreService {

    @Autowired
    private TheatreDAO theatreDAO;
    
    public List<TheatreDTO> getAllTheatres() {
        List<Theatre> theatres = theatreDAO.getAllTheatres();
        return theatres.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TheatreDTO> fetchTheatresAndShowTimingsForMovie(int movieId) {
        List<Theatre> theatres = theatreDAO.fetchTheatresAndShowTimingsForMovie(movieId);
        return theatres.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> fetchAvailableDatesForMovie(int movieId) {
        return theatreDAO.fetchAvailableDatesForMovie(movieId);
    }

    private TheatreDTO convertToDTO(Theatre theatre) {
        TheatreDTO dto = new TheatreDTO();
        dto.setTheatreId(theatre.getTheatreId());
        dto.setTheatreName(theatre.getTheatreName());
        dto.setCity(theatre.getCity());
        dto.setAddress(theatre.getAddress());
        return dto;
    }

    private Theatre convertToEntity(TheatreDTO theatreDTO) {
        Theatre theatre = new Theatre();
        theatre.setTheatreId(theatreDTO.getTheatreId());
        theatre.setTheatreName(theatreDTO.getTheatreName());
        theatre.setCity(theatreDTO.getCity());
        theatre.setAddress(theatreDTO.getAddress());
        return theatre;
    }
}
