package com.yogesh.service;

import com.yogesh.model.Theatre;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService {

    // Fetch a theatre by its ID
    public Theatre getTheatreById(int theatreId) {
        Theatre theatre = null;
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM theatres WHERE theatre_id=?");
            ps.setInt(1, theatreId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                theatre = new Theatre();
                theatre.setTheatreId(rs.getInt("theatre_id"));
                theatre.setName(rs.getString("name"));
                theatre.setCity(rs.getString("city"));
                theatre.setAddress(rs.getString("address"));
                theatre.setTotalScreens(rs.getInt("total_screens"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return theatre;
    }

    // Optional: Fetch all theatres
    public List<Theatre> getAllTheatres() {
        List<Theatre> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM theatres");
            while (rs.next()) {
                Theatre t = new Theatre();
                t.setTheatreId(rs.getInt("theatre_id"));
                t.setName(rs.getString("name"));
                t.setCity(rs.getString("city"));
                t.setAddress(rs.getString("address"));
                t.setTotalScreens(rs.getInt("total_screens"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
