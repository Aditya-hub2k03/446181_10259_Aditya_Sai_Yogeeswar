package com.yogesh.service;

import com.yogesh.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveDocument(MultipartFile file) throws IOException {
        String content = new BufferedReader(new InputStreamReader(file.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));

        String sql = "INSERT INTO documents (name, content, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, file.getOriginalFilename(), content, file.getContentType());
    }

    public List<Document> getAllDocuments() {
        String sql = "SELECT * FROM documents";
        return jdbcTemplate.query(sql, new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                Document document = new Document();
                document.setId(rs.getInt("id"));
                document.setName(rs.getString("name"));
                document.setContent(rs.getString("content"));
                document.setType(rs.getString("type"));
                return document;
            }
        });
    }

    public String getDocumentContent(int id) {
        String sql = "SELECT content FROM documents WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }
}
