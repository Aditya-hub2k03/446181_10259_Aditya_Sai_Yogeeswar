package com.yogesh.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OllamaService {
    private static final String OLAMA_API_URL = "http://localhost:11434/api/generate";
    private static final String OLAMA_MODELS_URL = "http://localhost:11434/api/tags";
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public String getOllamaResponse(String prompt, String model) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(OLAMA_API_URL);
            httpPost.setHeader("Content-Type", "application/json");

            String requestBody = "{\"model\": \"" + model + "\", \"prompt\": \"" + prompt + "\"}";
            httpPost.setEntity(new StringEntity(requestBody));

            String response = EntityUtils.toString(httpClient.execute(httpPost).getEntity());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response).get("response").asText();
        } catch (Exception e) {
            System.err.println("Ollama API Error: " + e.getMessage());
            return "Error: Could not connect to Ollama API. Ensure it is running (`ollama serve`).";
        }
    }

    public SseEmitter getOllamaResponseStream(String prompt, String model) {
        SseEmitter emitter = new SseEmitter(60000L); // 60s timeout

        executorService.execute(() -> {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(OLAMA_API_URL);
                httpPost.setHeader("Content-Type", "application/json");

                // Enable streaming
                String requestBody = "{\"model\": \"" + model + "\", \"prompt\": \"" + prompt + "\", \"stream\": true}";
                httpPost.setEntity(new StringEntity(requestBody));

                try {
                    // Send progress updates
                    emitter.send("Starting response generation...");

                    // Execute request and stream response
                    HttpResponse response = httpClient.execute(httpPost);
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("data:")) {
                                String json = line.substring(5).trim();
                                ObjectMapper mapper = new ObjectMapper();
                                JsonNode node = mapper.readTree(json);
                                String responseText = node.get("response").asText();
                                emitter.send(responseText);
                            }
                        }
                        emitter.send("Response generation complete.");
                        emitter.complete();
                    }
                } catch (Exception e) {
                    emitter.send("Error: " + e.getMessage());
                    emitter.completeWithError(e);
                }
            } catch (Exception e) {
                try {
					emitter.send("Error: " + e.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    public List<String> getAvailableModels() {
        List<String> models = new ArrayList<>();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(OLAMA_MODELS_URL);
            String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode modelsNode = rootNode.get("models");
            if (modelsNode != null && modelsNode.isArray()) {
                for (JsonNode modelNode : modelsNode) {
                    String modelName = modelNode.get("name").asText();
                    models.add(modelName);
                }
            }
        } catch (Exception e) {
            System.err.println("Ollama Models API Error: " + e.getMessage());
        }
        return models;
    }
}
