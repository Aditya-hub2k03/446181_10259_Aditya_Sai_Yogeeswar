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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    @GetMapping("/chat/stream")
    public SseEmitter streamChat(@RequestParam String message, @RequestParam String model) {
        ChatRequest request = new ChatRequest(message, model);
        return chatService.processMessageStream(request);
    }

    @GetMapping("/chatWithDocument/stream")
    public SseEmitter streamChatWithDocument(
            @RequestParam String message,
            @RequestParam String model,
            @RequestParam int documentId) {
        ChatRequest request = new ChatRequest(message, model);
        return chatService.processMessageWithDocumentStream(request, documentId);
    }

    @PostMapping(value = "/chat", consumes = "application/json")
    @ResponseBody
    public ChatResponse chat(@RequestBody ChatRequest request) {
        System.out.println("Received chat request: " + request.getMessage());
        return chatService.processMessage(request);
    }

    @PostMapping(value = "/chatWithDocument", consumes = "application/json")
    @ResponseBody
    public ChatResponse chatWithDocument(@RequestBody ChatRequest request, @RequestParam int documentId) {
        System.out.println("Received chatWithDocument request: " + request.getMessage() + ", Document ID: " + documentId);
        return chatService.processMessageWithDocument(request, documentId);
    }
}
