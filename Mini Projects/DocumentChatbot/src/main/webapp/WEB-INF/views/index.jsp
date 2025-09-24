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
    </style>
</head>
<body>
    <h1 style="text-align: center;">Document Chatbot</h1>
    <div id="chat-container">
        <!-- Document Upload Section -->
        <div class="upload-section">
            <h3>Upload a Document</h3>
            <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
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
                <p><b>You:</b> ${message.message}</p>
                <p><b>Bot (${message.model}):</b> ${message.response}</p>
                <hr>
            </c:forEach>
        </div>
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

        function selectDocument(id, name) {
            selectedDocumentId = id;
            $("#selected-doc-name").text(name);
            $("#chat-with-doc").show();
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
            $.post("/chatWithDocument?documentId=" + selectedDocumentId, { message: message, model: model }, function(response) {
                $("#messages").append("<p><b>You:</b> " + message + "</p>");
                $("#messages").append("<p><b>Bot (" + model + "):</b> " + response.response + "</p>");
                $("#messages").append("<hr>");
                $("#user-input").val("");
                $("#messages").scrollTop($("#messages")[0].scrollHeight);
            }).fail(function() {
                alert("Error sending message. Please try again.");
            });
        }

        function sendMessage() {
            const message = $("#user-input").val();
            const model = $("#model-select").val();
            if (!message.trim()) {
                alert("Please enter a message.");
                return;
            }
            $.post("/chat", { message: message, model: model }, function(response) {
                $("#messages").append("<p><b>You:</b> " + message + "</p>");
                $("#messages").append("<p><b>Bot (" + model + "):</b> " + response.response + "</p>");
                $("#messages").append("<hr>");
                $("#user-input").val("");
                $("#messages").scrollTop($("#messages")[0].scrollHeight);
            }).fail(function() {
                alert("Error sending message. Please try again.");
            });
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
