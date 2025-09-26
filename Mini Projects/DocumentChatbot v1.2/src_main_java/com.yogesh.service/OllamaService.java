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
import org.springframework.web.context.request.async.DeferredResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OllamaService {
    // Make the API URL configurable
    private String OLAMA_API_URL = "http://localhost:11434/api/generate";
    private String OLAMA_MODELS_URL = "http://localhost:11434/api/tags";
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // Add method to set custom API URL
    public void setOllamaApiUrl(String url) {
        this.OLAMA_API_URL = url + "/api/generate";
        this.OLAMA_MODELS_URL = url + "/api/tags";
    }

    public String getOllamaResponse(String prompt, String model) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(OLAMA_API_URL);
            httpPost.setHeader("Content-Type", "application/json");

            // Enhanced request with better parameters for detailed responses
            String requestBody = String.format(
                "{\"model\": \"%s\", \"prompt\": \"%s\", \"options\": {\"temperature\": 0.7, \"num_predict\": 500}}",
                model, prompt.replace("\"", "\\\"")
            );

            httpPost.setEntity(new StringEntity(requestBody));

            HttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);

            // Extract the full response
            String fullResponse = rootNode.get("response").asText();

            // If response is too short, try to get more context
            if (fullResponse.split(" ").length < 5) {
                String enhancedPrompt = prompt + "\n\nPlease provide a detailed response with at least 3 sentences.";
                String enhancedRequest = String.format(
                    "{\"model\": \"%s\", \"prompt\": \"%s\", \"options\": {\"temperature\": 0.7, \"num_predict\": 500}}",
                    model, enhancedPrompt.replace("\"", "\\\"")
                );

                HttpPost enhancedPost = new HttpPost(OLAMA_API_URL);
                enhancedPost.setHeader("Content-Type", "application/json");
                enhancedPost.setEntity(new StringEntity(enhancedRequest));

                HttpResponse enhancedResponse = httpClient.execute(enhancedPost);
                String enhancedResponseBody = EntityUtils.toString(enhancedResponse.getEntity());
                JsonNode enhancedRootNode = mapper.readTree(enhancedResponseBody);
                fullResponse = enhancedRootNode.get("response").asText();
            }

            return fullResponse;
        } catch (ConnectException e) {
            System.err.println("Connection refused to Ollama API. Is Ollama running?");
            return "Error: Could not connect to Ollama API. Ensure it is running (`ollama serve`).";
        } catch (SocketTimeoutException e) {
            System.err.println("Connection timeout to Ollama API. Is the server responsive?");
            return "Error: Connection to Ollama API timed out. Please check if Ollama is running and responsive.";
        } catch (Exception e) {
            System.err.println("Ollama API Error: " + e.getMessage());
            return "Error: Could not get response from Ollama API. Details: " + e.getMessage();
        }
    }

    public DeferredResult<String> getOllamaResponseAsync(String prompt, String model) {
        DeferredResult<String> deferredResult = new DeferredResult<>(60000L);

        executorService.execute(() -> {
            try {
                String response = getOllamaResponse(prompt, model);
                deferredResult.setResult(response);
            } catch (Exception e) {
                deferredResult.setErrorResult("Error: " + e.getMessage());
            }
        });

        return deferredResult;
    }

    public List<String> getAvailableModels() {
        List<String> models = new ArrayList<>();

        // First try to get models from the API
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(OLAMA_MODELS_URL);

            try {
                HttpResponse response = httpClient.execute(httpGet);

                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(responseBody);
                    JsonNode modelsNode = rootNode.get("models");

                    if (modelsNode != null && modelsNode.isArray()) {
                        for (JsonNode modelNode : modelsNode) {
                            String modelName = modelNode.get("name").asText();
                            models.add(modelName);
                        }
                    }
                } else {
                    System.err.println("Failed to fetch models. Status code: " + response.getStatusLine().getStatusCode());
                }
            } catch (ConnectException e) {
                System.err.println("Connection refused to Ollama API for models. Is Ollama running?");
            } catch (Exception e) {
                System.err.println("Error fetching models: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error creating HTTP client for models: " + e.getMessage());
        }

        // Add default models if API fails or returns empty
        if (models.isEmpty()) {
            models.add("llama2");
            models.add("mistral");
            models.add("llama2-uncensored");
            models.add("phi");
            models.add("codellama");
        }

        return models;
    }

    public String getResponseWithDocumentContext(String documentContent, String question, String model) {
        // Enhanced prompt structure for better responses with document context
        String prompt = String.format(
            "Document Content:\n" +
            "---------------------\n" +
            "%s\n" +
            "---------------------\n" +
            "Based on the document content above, please provide a detailed answer to the following question:\n" +
            "Question: %s\n\n" +
            "Please provide a comprehensive response with at least 3-5 sentences explaining the answer in detail.",
            documentContent.length() > 2000 ? documentContent.substring(0, 2000) + "... [truncated]" : documentContent,
            question
        );

        return getOllamaResponse(prompt, model);
    }

    // Add a method to check if Ollama is running
    public boolean checkOllamaConnection() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(OLAMA_MODELS_URL);
            HttpResponse response = httpClient.execute(httpGet);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
