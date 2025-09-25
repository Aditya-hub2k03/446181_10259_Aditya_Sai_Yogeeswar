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
        #messages { height: 300px; overflow-y: auto; border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; border-radius: 4px; background: #f9f9f9; }
        #user-input { width: calc(100% - 100px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        select { padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #ddd; }
        .upload-section { margin-bottom: 20px; padding: 15px; background: #f0f8ff; border-radius: 5px; }
        .documents-list { margin-top: 10px; }
        .document-item { padding: 5px; border-bottom: 1px solid #eee; cursor: pointer; }
        .document-item:hover { background: #e6f7ff; }
        .chat-with-doc { margin-top: 10px; }
        .typing-indicator { display: none; color: #666; font-style: italic; }
        .response-container { margin-bottom: 10px; }
        .bot-response { white-space: pre-wrap; }
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
            <div class="documents-list">
                <c:forEach var="doc" items="${documents}">
                    <div class="document-item" onclick="selectDocument(${doc.id}, '${doc.name}')">
                        <strong>${doc.name}</strong> (${doc.type})
                    </div>
                </c:forEach>
            </div>
            <div class="chat-with-doc" id="chat-with-doc" style="display: none;">
                <p>Selected Document: <span id="selected-doc-name"></span></p>
                <button onclick="chatWithSelectedDocument()">Chat with Document</button>
            </div>
        </div>

        <!-- Chat Section -->
        <div id="messages">
            <c:forEach var="message" items="${messages}">
                <div class="response-container">
                    <p><b>You:</b> ${message.message}</p>
                    <p><b>Bot (${message.model}):</b> <span class="bot-response">${message.response}</span></p>
                </div>
                <hr>
            </c:forEach>
        </div>
        <div id="typing-indicator" class="typing-indicator">Bot is typing...</div>
        <div>
            <label for="model-select">Select Model:</label>
            <select id="model-select">
                <c:forEach var="model" items="${models}">
                    <option value="${model}">${model}</option>
                </c:forEach>
            </select>
        </div>
        <div style="display: flex; gap: 10px;">
            <input type="text" id="user-input" placeholder="Type your message...">
            <button onclick="sendMessage()">Send</button>
        </div>
    </div>

    <script>
        let selectedDocumentId = null;
        let currentEventSource = null;
        let currentBotResponse = null;
        const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));

        function selectDocument(id, name) {
            selectedDocumentId = id;
            $("#selected-doc-name").text(name);
            $("#chat-with-doc").show();
        }

        function showTypingIndicator() {
            $("#typing-indicator").show();
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function hideTypingIndicator() {
            $("#typing-indicator").hide();
        }

        function appendUserMessage(content) {
            $("#messages").append(`<div class="response-container"><p><b>You:</b> ${content}</p></div>`);
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
        }

        function appendBotMessage(model, content) {
            if (!currentBotResponse) {
                currentBotResponse = $(`<div class="response-container"><p><b>Bot (${model}):</b> <span class="bot-response"></span></p></div>`);
                $("#messages").append(currentBotResponse);
            }
            currentBotResponse.find(".bot-response").append(content);
            $("#messages").scrollTop($("#messages")[0].scrollHeight);
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

            appendUserMessage(message);
            showTypingIndicator();
            currentBotResponse = null;

            // Close previous connection if any
            if (currentEventSource) {
                currentEventSource.close();
            }

            // Build URL with proper encoding
            const url = contextPath + "/chatWithDocument/stream?"
                + "message=" + encodeURIComponent(message)
                + "&model=" + encodeURIComponent(model)
                + "&documentId=" + selectedDocumentId;

            // Use SSE for streaming response
            const eventSource = new EventSource(url);

            currentEventSource = eventSource;

            eventSource.onmessage = function(event) {
                if (event.data.startsWith("Starting response generation...")) {
                    console.log("Response generation started");
                } else if (event.data.startsWith("Response generation complete.")) {
                    console.log("Response generation complete");
                    hideTypingIndicator();
                } else if (event.data.startsWith("Error:")) {
                    console.error("Error:", event.data);
                    appendBotMessage(model, event.data);
                    hideTypingIndicator();
                } else {
                    appendBotMessage(model, event.data);
                }
            };

            eventSource.onerror = function() {
                hideTypingIndicator();
                eventSource.close();
                currentBotResponse = null;
            };

            $("#user-input").val("");
        }

        function sendMessage() {
            const message = $("#user-input").val();
            const model = $("#model-select").val();
            if (!message.trim()) {
                alert("Please enter a message.");
                return;
            }

            appendUserMessage(message);
            showTypingIndicator();
            currentBotResponse = null;

            // Close previous connection if any
            if (currentEventSource) {
                currentEventSource.close();
            }

            // Build URL with proper encoding
            const url = contextPath + "/chat/stream?"
                + "message=" + encodeURIComponent(message)
                + "&model=" + encodeURIComponent(model);

            // Use SSE for streaming response
            const eventSource = new EventSource(url);

            currentEventSource = eventSource;

            eventSource.onmessage = function(event) {
                if (event.data.startsWith("Starting response generation...")) {
                    console.log("Response generation started");
                } else if (event.data.startsWith("Response generation complete.")) {
                    console.log("Response generation complete");
                    hideTypingIndicator();
                } else if (event.data.startsWith("Error:")) {
                    console.error("Error:", event.data);
                    appendBotMessage(model, event.data);
                    hideTypingIndicator();
                } else {
                    appendBotMessage(model, event.data);
                }
            };

            eventSource.onerror = function() {
                hideTypingIndicator();
                eventSource.close();
                currentBotResponse = null;
            };

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
