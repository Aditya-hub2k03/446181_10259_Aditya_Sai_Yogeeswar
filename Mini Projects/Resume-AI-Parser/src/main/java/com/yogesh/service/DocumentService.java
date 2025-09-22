package com.yogesh.service;

import com.yogesh.dao.DocumentDAO;
import com.yogesh.dao.SummaryDAO;
import com.yogesh.model.Document;
import com.yogesh.model.Summary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private static final Logger logger = LogManager.getLogger(DocumentService.class);
    @Autowired
    private AIClient aiClient;
    @Autowired
    private DocumentDAO documentDAO;
    @Autowired
    private SummaryDAO summaryDAO;

    public void setModel(String model) {
        aiClient.setModel(model);
    }

    public String summarize(String text) {
        String summary = aiClient.summarize(text);
        Document document = new Document(text);
        Summary summaryObj = new Summary(summary);
        documentDAO.save(document);
        summaryDAO.save(summaryObj);
        return summary;
    }
}
