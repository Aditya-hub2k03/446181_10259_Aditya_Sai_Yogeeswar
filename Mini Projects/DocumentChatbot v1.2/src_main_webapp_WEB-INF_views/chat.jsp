<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html> 
<head>
    <title>Chat Interface</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f9; }
        #chat-container { max-width: 800px; margin: 0 auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        #messages { height: 400px; overflow-y: auto; border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; border-radius: 4px; background: #f9f9f9; }
        #user-input { width: calc(100% - 100px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 10px 15px; background: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        select { padding: 8px; margin-bottom: 10px; border-radius: 4px; border: 1px solid #ddd; }
        .back-button { display: inline-block; margin-bottom: 20px; color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
    <div id="chat-container">
        <a href="/" class="back-button">← Back to Home</a>
        <h1 style="text-align: center;">Chat Interface</h1>
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
        function sendMessage() {
            const message = $("#user-input").val();
            const model = $("#model-select").val();
            if (!message.trim()) {
                alert("Please enter a message.");
                return;
            }
            $.post(
                "${pageContext.request.contextPath}/chat",
                { message: message, model: model },
                function(response) {
                    $("#messages").append("<p><b>You:</b> " + message + "</p>");
                    $("#messages").append("<p><b>Bot (" + model + "):</b> " + response.response + "</p>");
                    $("#messages").append("<hr>");
                    $("#user-input").val("");
                    $("#messages").scrollTop($("#messages")[0].scrollHeight);
                }
            ).fail(function(xhr, status, error) {
                console.error("Error:", error);
                alert("Error sending message. Check console for details.");
            });
        }

        $("#user-input").keypress(function(e) {
            if (e.which === 13) {
                sendMessage();
            }
        });
    </script>
</body>
</html>
