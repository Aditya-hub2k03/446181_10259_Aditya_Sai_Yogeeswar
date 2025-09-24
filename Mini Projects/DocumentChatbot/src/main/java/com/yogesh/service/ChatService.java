package com.yogesh.service;

import com.yogesh.dto.ChatRequest;
import com.yogesh.dto.ChatResponse;
import com.yogesh.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private DocumentService documentService;

    public ChatResponse processMessage(ChatRequest request) {
        String response = ollamaService.getOllamaResponse(request.getMessage(), request.getModel());
        saveMessage(request.getMessage(), response, request.getModel());

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponse(response);
        return chatResponse;
    }

    public ChatResponse processMessageWithDocument(ChatRequest request, int documentId) {
        String documentContent = documentService.getDocumentContent(documentId);
        String prompt = "Document Content:\n" + documentContent + "\n\nQuestion: " + request.getMessage();
        String response = ollamaService.getOllamaResponse(prompt, request.getModel());
        saveMessage(request.getMessage(), response, request.getModel());

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setResponse(response);
        return chatResponse;
    }

    public List<ChatMessage> getAllMessages() {
        String sql = "SELECT * FROM chat_messages";
        return jdbcTemplate.query(sql, new RowMapper<ChatMessage>() {
            @Override
            public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChatMessage message = new ChatMessage();
                message.setId(rs.getInt("id"));
                message.setMessage(rs.getString("message"));
                message.setResponse(rs.getString("response"));
                message.setModel(rs.getString("model"));
                return message;
            }
        });
    }

    private void saveMessage(String message, String response, String model) {
        String sql = "INSERT INTO chat_messages (message, response, model) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, message, response, model);
    }
}
