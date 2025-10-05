<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Error</title></head>
<body>
<h2>Error</h2>
<p style="color:red;">
    <c:out value="${requestScope['jakarta.servlet.error.message']}" default="An error occurred"/>
</p>
</body>
</html>
