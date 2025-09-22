package com.yogesh.dao;

import com.yogesh.model.Document;
import java.sql.*;

public class DocumentDAO {
    public void save(Document document) {
        String sql = "INSERT INTO documents(content) VALUES(?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, document.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
