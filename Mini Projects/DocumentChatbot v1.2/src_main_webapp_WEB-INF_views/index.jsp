<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Document Chatbot</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f9; }
        #chat-container { max-width: 800px; margin: 0 auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        #messages { height: 400px; overflow-y: auto; border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; border-radius: 4px; background: #f9f9f9; }
        #user-input { width: calc(100% - 100px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        select { padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #ddd; width: 100%; }
        .upload-section { margin-bottom: 20px; padding: 15px; background: #f0f8ff; border-radius: 5px; }
        .documents-list { margin-top: 10px; max-height: 150px; overflow-y: auto; border: 1px solid #ddd; padding: 10px; border-radius: 4px; background: #fff; }
        .document-item { padding: 8px; border-bottom: 1px solid #eee; cursor: pointer; }
        .document-item:hover { background: #e6f7ff; }
        .document-item.selected { background: #d4edff; }
        .chat-with-doc { margin-top: 10px; }
        .typing-indicator { display: none; color: #666; font-style: italic; margin: 10px 0; }
        .response-container { margin-bottom: 10px; }
        .bot-response { white-space: pre-wrap; line-height: 1.5; }
        .status-message { color: #666; font-style: italic; font-size: 0.9em; margin: 5px 0; }
        .loading-dots {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background-color: #666;
            margin: 0 2px;
            animation: bounce 1.4s infinite ease-in-out;
        }
        .loading-dots:nth-child(1) { animation-delay: -0.32s; }
        .loading-dots:nth-child(2) { animation-delay: -0.16s; }
        .model-select-container { margin-bottom: 15px; }
        .model-select-label { display: block; margin-bottom: 5px; font-weight: bold; }
        .response-header { font-weight: bold; margin-bottom: 5px; }

        @keyframes bounce {
            0%, 80%, 100% { transform: scale(0); }
            40% { transform: scale(1); }
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">Document Chatbot</h1>
    <div id="chat-container">
        <!-- Document Upload Section -->
        <div class="upload-section">
            <h3>Upload a Document</h3>
            <form action="upload" method="post" enctype="multipart/form-data">
                <input type="file" name="file" required>
                <button type="submit">Upload</button>
            </form>
            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>
            <c:if test="${not empty success}">
                <p style="color: green;">${success}</p>
            </c:if>

            <h4>Uploaded Documents:</h4>
            <div class="documents-list" id="documents-list">
                <c:forEach var="doc" items="${documents}">
                    <div class="document-item" onclick="selectDocument(${doc.id}, '${doc.name}')">
                        <strong>${doc.name}</strong> (${doc.type})
                    </div>
                </c:forEach>
                <c:if test="${empty documents}">
                    <div style="padding: 8px; color: #666;">No documents uploaded yet</div>
                </c:if>
            </div>
            <div class="chat-with-doc" id="chat-with-doc" style="display: none;">
                <p>Selected Document: <span id="selected-doc-name"></span>
                <button onclick="clearDocumentSelection()" style="background: #dc3545; margin-left: 10px; padding: 5px 10px; font-size: 0.8em;">Clear</button>
                </p>
            </div>
        </div>

        <!-- Model Selection -->
        <div class="model-select-container">
            <label class="model-select-label" for="model-select">Select Model:</label>
            <select id="model-select">
                <option value="">Loading models...</option>
                <c:forEach var="model" items="${models}">
                    <option value="${model}">${model}</option>
                </c:forEach>
            </select>
            <button id="refresh-models" onclick="refreshModels()" style="margin-top: 5px; background: #6c757d; padding: 5px 10px; font-size: 0.8em;">Refresh Models</button>
        </div>

        <!-- Chat Section -->
        <div id="messages">
            <c:forEach var="message" items="${messages}">
                <div class="response-container">
                    <p><b>You:</b> ${message.message}</p>
                    <div class="response-header">Bot (${message.model}):</div>
                    <p class="bot-response">${message.response}</p>
                </div>
                <hr>
            </c:forEach>
            <c:if test="${empty messages}">
                <div style="text-align: center; color: #666; margin: 20px 0;">No messages yet. Start a conversation!</div>
            </c:if>
        </div>
        <div id="typing-indicator" class="typing-indicator">
            Bot is typing
            <div style="display: inline-block;">
                <div class="loading-dots"></div>
                <div class="loading-dots"></div>
                <div class="loading-dots"></div>
            </div>
        </div>
        <div id="status-message" class="status-message"></div>
        <div style="display: flex; gap: 10px; margin-top: 10px;">
            <input type="text" id="user-input" placeholder="Type your message..." style="flex: 1;">
            <button onclick="sendMessage()" style="width: 100px;">Send</button>
        </div>
    </div>

    <script>
        let selectedDocumentId = null;
        let currentRequest = null;
        let currentBotResponse = null;

        // Get the context path from the current URL
        const contextPath = window.location.pathname.split('/')[1];
        const baseUrl = window.location.origin + '/' + contextPath;

        // Initialize the page
        $(document).ready(function() {
            // Load models if not already loaded
            if ($("#model-select option").length <= 1) {
                refreshModels();
            }
        });

        function selectDocument(id, name) {
            // Remove selected class from all documents
            $(".document-item").removeClass("selected");

            // Add selected class to clicked document
            $(`div[onclick="selectDocument(${id}, '${name}')"]`).addClass("selected");

            selectedDocumentId = id;
            $("#selected-doc-name").text(name);
            $("#chat-with-doc").show();
        }

        function clearDocumentSelection() {
            $(".document-item").removeClass("selected");
            selectedDocumentId = null;
            $("#chat-with-doc").hide();
        }

        function showTypingIndicator() {
            $("#typing-indicator").show();
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function hideTypingIndicator() {
            $("#typing-indicator").hide();
        }

        function showStatusMessage(message) {
            $("#status-message").text(message).show();
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function hideStatusMessage() {
            $("#status-message").hide();
        }

        function appendUserMessage(content) {
            $("#messages").append(`<div class="response-container"><p><b>You:</b> ${content}</p></div>`);
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function appendBotMessage(model, content) {
            if (!currentBotResponse) {
                currentBotResponse = $(`<div class="response-container">
                    <div class="response-header">Bot (${model}):</div>
                    <p class="bot-response"></p>
                </div>`);
                $("#messages").append(currentBotResponse);
            }
            currentBotResponse.find(".bot-response").append(content);
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function buildUrl(endpoint, params) {
            const url = new URL(baseUrl + '/' + endpoint);
            Object.entries(params).forEach(([key, value]) => {
                url.searchParams.append(key, value);
            });
            return url.toString();
        }

        function refreshModels() {
            showStatusMessage("Refreshing models...");

            fetch(buildUrl('models', {}))
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to load models');
                    }
                    return response.json();
                })
                .then(models => {
                    const select = $("#model-select");
                    select.empty();

                    if (models.length === 0) {
                        select.append('<option value="">No models available</option>');
                        return;
                    }

                    // Add default option
                    select.append('<option value="">Select a model</option>');

                    // Add models
                    models.forEach(model => {
                        select.append(`<option value="${model}">${model}</option>`);
                    });

                    hideStatusMessage();
                })
                .catch(error => {
                    showStatusMessage("Error loading models: " + error.message);
                    const select = $("#model-select");
                    select.empty();
                    select.append('<option value="">llama2</option>');
                    select.append('<option value="mistral">mistral</option>');
                    select.append('<option value="llama2-uncensored">llama2-uncensored</option>');
                });
        }

        function chatWithSelectedDocument() {
            if (!selectedDocumentId) {
                alert("Please select a document first.");
                return;
            }
            const message = $("#user-input").val();
            const model = $("#model-select").val();

            if (!message.trim()) {
                alert("Please enter a message.");
                return;
            }

            if (!model) {
                alert("Please select a model.");
                return;
            }

            appendUserMessage(message);
            showTypingIndicator();
            currentBotResponse = null;

            // Abort previous request if any
            if (currentRequest) {
                currentRequest.abort();
            }

            // Build URL with proper encoding
            const url = buildUrl('chatWithDocument/async', {
                message: message,
                model: model,
                documentId: selectedDocumentId
            });

            // Use Fetch API for async response
            showStatusMessage(`Processing your request with ${model}...`);
            currentRequest = fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(response => {
                    // Check if response is too short and request more detail
                    if (response.split(' ').length < 5) {
                        const enhancedUrl = buildUrl('chatWithDocument/async', {
                            message: message + "\n\nPlease provide a more detailed response with at least 3 sentences.",
                            model: model,
                            documentId: selectedDocumentId
                        });

                        return fetch(enhancedUrl)
                            .then(enhancedResponse => {
                                if (!enhancedResponse.ok) {
                                    throw new Error('Failed to get enhanced response');
                                }
                                return enhancedResponse.text();
                            });
                    }
                    return response;
                })
                .then(response => {
                    appendBotMessage(model, response);
                    hideTypingIndicator();
                    hideStatusMessage();
                })
                .catch(error => {
                    appendBotMessage(model, "Error: " + error.message);
                    hideTypingIndicator();
                    hideStatusMessage();
                })
                .finally(() => {
                    currentRequest = null;
                });

            $("#user-input").val("");
        }

        function sendMessage() {
            const message = $("#user-input").val();
            const model = $("#model-select").val();

            if (!message.trim()) {
                alert("Please enter a message.");
                return;
            }

            if (!model) {
                alert("Please select a model.");
                return;
            }

            appendUserMessage(message);
            showTypingIndicator();
            currentBotResponse = null;

            // Abort previous request if any
            if (currentRequest) {
                currentRequest.abort();
            }

            // Build URL with proper encoding
            const url = buildUrl('chat/async', {
                message: message,
                model: model
            });

            // Use Fetch API for async response
            showStatusMessage(`Processing your request with ${model}...`);
            currentRequest = fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text();
                })
                .then(response => {
                    // Check if response is too short and request more detail
                    if (response.split(' ').length < 5) {
                        const enhancedUrl = buildUrl('chat/async', {
                            message: message + "\n\nPlease provide a more detailed response with at least 3 sentences.",
                            model: model
                        });

                        return fetch(enhancedUrl)
                            .then(enhancedResponse => {
                                if (!enhancedResponse.ok) {
                                    throw new Error('Failed to get enhanced response');
                                }
                                return enhancedResponse.text();
                            });
                    }
                    return response;
                })
                .then(response => {
                    appendBotMessage(model, response);
                    hideTypingIndicator();
                    hideStatusMessage();
                })
                .catch(error => {
                    appendBotMessage(model, "Error: " + error.message);
                    hideTypingIndicator();
                    hideStatusMessage();
                })
                .finally(() => {
                    currentRequest = null;
                });

            $("#user-input").val("");
        }

        $("#user-input").keypress(function(e) {
            if (e.which === 13) {
                if (selectedDocumentId) {
                    chatWithSelectedDocument();
                } else {
                    sendMessage();
                }
            }
        });
    </script>
</body>
</html>
