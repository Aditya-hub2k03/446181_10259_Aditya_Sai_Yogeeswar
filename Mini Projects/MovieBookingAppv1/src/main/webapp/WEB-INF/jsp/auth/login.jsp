<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<form method="post" action="${pageContext.request.contextPath}/auth/login">
    Email: <input type="email" name="email" required/> <br/>
    Password: <input type="password" name="password" required/> <br/>
    <input type="submit" value="Login"/>
</form>

<c:if test="${not empty param.error}">
    <p style="color:red;">${param.error}</p>
</c:if>
</body>
</html>
