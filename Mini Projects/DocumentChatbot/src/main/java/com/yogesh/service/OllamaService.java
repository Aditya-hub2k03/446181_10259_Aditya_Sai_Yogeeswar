package com.yogesh.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OllamaService {
    private static final String OLAMA_API_URL = "http://localhost:11434/api/generate";
    private static final String OLAMA_MODELS_URL = "http://localhost:11434/api/tags";

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
            return "Error: " + e.getMessage();
        }
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
            e.printStackTrace();
        }
        return models;
    }
}
