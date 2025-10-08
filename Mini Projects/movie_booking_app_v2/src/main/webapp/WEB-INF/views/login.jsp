<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>

<form action="login" method="post">
    Email: <input type="email" name="email" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <button type="submit">Login</button>
</form>

<c:if test="${not empty message}">
    <p style="color:red;">${message}</p>
</c:if>

<a href="signup">Don't have an account? Signup</a>
</body>
</html>
