<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Welcome to MovieBookingApp</title>
</head>
<body>
    <h1>Welcome to MovieBookingApp</h1>
    <p>Please choose an option:</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/auth/login">Login</a></li>
        <li><a href="${pageContext.request.contextPath}/auth/register">Register</a></li>
    </ul>
</body>
</html>
