package com.yogesh.service;

import com.yogesh.dao.TheatreAdminDAO;
import com.yogesh.dao.TheatreDAO;
import com.yogesh.dao.UserDAO;
import com.yogesh.dto.TheatreAdminDTO;
import com.yogesh.model.Theatre;
import com.yogesh.model.TheatreAdmin;
import com.yogesh.model.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreAdminService {

    @Autowired
    private TheatreAdminDAO theatreAdminDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TheatreDAO theatreDAO;

    public List<TheatreAdminDTO> getTheatresByAdminId(int adminId) {
        List<TheatreAdmin> theatreAdmins = theatreAdminDAO.getTheatresByAdminId(adminId);
        return theatreAdmins.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean assignTheatreToAdmin(int adminId, int theatreId) {
        return theatreAdminDAO.assignTheatreToAdmin(adminId, theatreId);
    }

    public boolean removeTheatreFromAdmin(int adminId, int theatreId) {
        return theatreAdminDAO.removeTheatreFromAdmin(adminId, theatreId);
    }

    private TheatreAdminDTO convertToDTO(TheatreAdmin theatreAdmin) {
        TheatreAdminDTO dto = new TheatreAdminDTO();
        dto.setAdminId(theatreAdmin.getAdminId());
        dto.setUserId(theatreAdmin.getUserId());
        dto.setTheatreId(theatreAdmin.getTheatreId());

        User user = userDAO.findUserById(theatreAdmin.getUserId());
        if (user != null) {
            dto.setUserName(user.getUserName());
        }

        Theatre theatre = theatreDAO.findTheatreById(theatreAdmin.getTheatreId());
        if (theatre != null) {
            dto.setTheatreName(theatre.getTheatreName());
        }

        return dto;
    }

    private TheatreAdmin convertToEntity(TheatreAdminDTO theatreAdminDTO) {
        TheatreAdmin theatreAdmin = new TheatreAdmin();
        theatreAdmin.setAdminId(theatreAdminDTO.getAdminId());
        theatreAdmin.setUserId(theatreAdminDTO.getUserId());
        theatreAdmin.setTheatreId(theatreAdminDTO.getTheatreId());
        return theatreAdmin;
    }
}
