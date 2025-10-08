<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Signup</title>
</head>
<body>
<h1>Signup</h1>

<form action="signup" method="post">
    Name: <input type="text" name="userName" required/><br/>
    Email: <input type="email" name="email" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <button type="submit">Register</button>
</form>

<c:if test="${not empty message}">
    <p style="color:red;">${message}</p>
</c:if>

<a href="login">Already have an account? Login</a>
</body>
</html>
