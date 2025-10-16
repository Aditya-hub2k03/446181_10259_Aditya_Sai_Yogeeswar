package com.yogesh.dao;

import com.yogesh.model.TheatreAdmin;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TheatreAdminDAO {

    public List<TheatreAdmin> getTheatresByAdminId(int adminId) {
        List<TheatreAdmin> theatreAdmins = new ArrayList<>();
        String sql = "SELECT * FROM theatre_admins WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TheatreAdmin theatreAdmin = new TheatreAdmin();
                theatreAdmin.setAdminId(rs.getInt("admin_id"));
                theatreAdmin.setUserId(rs.getInt("user_id"));
                theatreAdmin.setTheatreId(rs.getInt("theatre_id"));
                theatreAdmins.add(theatreAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatreAdmins;
    }

    public boolean assignTheatreToAdmin(int adminId, int theatreId) {
        // First check if the assignment already exists
        String checkSql = "SELECT COUNT(*) FROM theatre_admins WHERE user_id = ? AND theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, adminId);
            checkStmt.setInt(2, theatreId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Assignment already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Assign the theatre to admin
        String sql = "INSERT INTO theatre_admins (user_id, theatre_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            stmt.setInt(2, theatreId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTheatreFromAdmin(int adminId, int theatreId) {
        String sql = "DELETE FROM theatre_admins WHERE user_id = ? AND theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            stmt.setInt(2, theatreId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
