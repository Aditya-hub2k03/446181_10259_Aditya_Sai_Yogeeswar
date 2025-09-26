package com.yogesh.controller;

import com.yogesh.dto.ChatRequest;
import com.yogesh.dto.ChatResponse;
import com.yogesh.model.ChatMessage;
import com.yogesh.model.Document;
import com.yogesh.service.ChatService;
import com.yogesh.service.DocumentService;
import com.yogesh.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Add connection status to the model
        boolean isConnected = ollamaService.checkOllamaConnection();
        model.addAttribute("ollamaConnected", isConnected);

        model.addAttribute("messages", messages);
        model.addAttribute("models", models);
        model.addAttribute("documents", documents);
        return "index";
    }

    @GetMapping("/check-connection")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkConnection() {
        Map<String, Object> response = new HashMap<>();
        response.put("connected", ollamaService.checkOllamaConnection());
        return ResponseEntity.ok(response);
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

    @GetMapping(value = "/models", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getModels() {
        return ollamaService.getAvailableModels();
    }

    @GetMapping(value = "/chat/async", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public DeferredResult<String> chatAsync(@RequestParam String message, @RequestParam String model) {
        // Enhance the prompt for better responses
        String enhancedPrompt = message + "\n\nPlease provide a detailed response with at least 3 sentences.";
        return ollamaService.getOllamaResponseAsync(enhancedPrompt, model);
    }

    @GetMapping(value = "/chatWithDocument/async", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public DeferredResult<String> chatWithDocumentAsync(
            @RequestParam String message,
            @RequestParam String model,
            @RequestParam int documentId) {
        String documentContent = documentService.getDocumentContent(documentId);
        return ollamaService.getOllamaResponseAsync(
            ollamaService.getResponseWithDocumentContext(
                documentContent,
                message,
                model
            ),
            model
        );
    }

    @PostMapping(value = "/chat", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.processMessage(request);
    }

    @PostMapping(value = "/chatWithDocument", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ChatResponse chatWithDocument(@RequestBody ChatRequest request, @RequestParam int documentId) {
        return chatService.processMessageWithDocument(request, documentId);
    }
}
