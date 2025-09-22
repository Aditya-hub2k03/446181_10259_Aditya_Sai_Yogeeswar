package com.yogesh.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIClient {
    private static final Logger logger = LogManager.getLogger(AIClient.class);
    private final String ollamaApiUrl = "http://localhost:11434/api/generate";
    private String selectedModel = "llama3"; // Default model

    public void setModel(String model) {
        this.selectedModel = model;
    }

    public String summarize(String text) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(ollamaApiUrl);
            httpPost.setHeader("Content-Type", "application/json");

            String jsonInput = String.format(
                "{\"model\": \"%s\", \"prompt\": \"Summarize the following text: %s\"}",
                selectedModel, text
            );

            httpPost.setEntity(new StringEntity(jsonInput));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(result);
                return rootNode.path("response").asText();
            }
        } catch (Exception e) {
            logger.error("Error calling Ollama API: ", e);
        }
        return "Summary unavailable.";
    }
}
