package com.yogesh.controller;

import com.yogesh.dto.ChatRequest;
import com.yogesh.dto.ChatResponse;
import com.yogesh.model.ChatMessage;
import com.yogesh.model.Document;
import com.yogesh.service.ChatService;
import com.yogesh.service.DocumentService;
import com.yogesh.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public String index(Model model) {
        List<ChatMessage> messages = chatService.getAllMessages();
        List<String> models = ollamaService.getAvailableModels();
        List<Document> documents = documentService.getAllDocuments();
        model.addAttribute("messages", messages);
        model.addAttribute("models", models);
        model.addAttribute("documents", documents);
        return "index";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file, Model model) {
        try {
            documentService.saveDocument(file);
            model.addAttribute("success", "Document uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload document: " + e.getMessage());
        }
        return "redirect:/";
    }


    @PostMapping("/chat")
    @ResponseBody
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.processMessage(request);
    }

    @PostMapping("/chatWithDocument")
    @ResponseBody
    public ChatResponse chatWithDocument(@RequestBody ChatRequest request, @RequestParam int documentId) {
        return chatService.processMessageWithDocument(request, documentId);
    }
}
