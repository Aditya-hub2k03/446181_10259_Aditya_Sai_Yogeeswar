package com.yogesh.controller;

import com.yogesh.model.Document;
import com.yogesh.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/summarize")
    public String summarize(
            @RequestParam("text") String text,
            @RequestParam("model") String model,
            Model m) {
        documentService.setModel(model);
        String summary = documentService.summarize(text);
        m.addAttribute("summary", summary);
        return "result";
    }
}
