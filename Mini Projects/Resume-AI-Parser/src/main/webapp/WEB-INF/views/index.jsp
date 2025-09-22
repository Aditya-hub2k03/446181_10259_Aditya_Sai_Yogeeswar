<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Document Summarizer</title>
</head>
<body>
    <h1>Document Summarizer</h1>
    <form action="/summarize" method="post">
        <textarea name="text" rows="10" cols="50" required></textarea><br>
        <label for="model">Select Model:</label>
        <select name="model" id="model">
            <option value="llama3">Llama3</option>
            <option value="mistral">Mistral</option>
            <option value="phi3">Phi3</option>
        </select><br>
        <button type="submit">Summarize</button>
    </form>
</body>
</html>
