package com.yogesh.service;

import com.yogesh.model.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
        String content = extractTextFromFile(file);
        // Store only the first 100,000 characters to avoid database issues
        String truncatedContent = content.length() > 100000 ? content.substring(0, 100000) : content;

        String sql = "INSERT INTO documents (name, content, type) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, file.getOriginalFilename(), truncatedContent, file.getContentType());
    }

    private String extractTextFromFile(MultipartFile file) throws IOException {
        if (file.getContentType().equals("application/pdf")) {
            return extractTextFromPDF(file);
        } else {
            return new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(document.getNumberOfPages());
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    public List<Document> getAllDocuments() {
        String sql = "SELECT * FROM documents ORDER BY id DESC";
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
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (Exception e) {
            System.err.println("Error retrieving document content: " + e.getMessage());
            return "";
        }
    }
}
