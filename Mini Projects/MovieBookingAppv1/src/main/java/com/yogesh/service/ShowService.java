package com.yogesh.service;

import com.yogesh.dto.ShowDto;
import java.util.List;

public interface ShowService {
    List<ShowDto.ShowResponse> getAllShows() throws Exception;
    ShowDto.ShowResponse getShowById(Long showId) throws Exception;
    void addShow(ShowDto.ShowRequest request) throws Exception;
    void updateShow(Long showId, ShowDto.ShowRequest request) throws Exception;
    void deleteShow(Long showId) throws Exception;
}
