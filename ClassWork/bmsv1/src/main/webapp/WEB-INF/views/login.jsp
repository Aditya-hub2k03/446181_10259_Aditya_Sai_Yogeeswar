<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<form action="login" method="post">
    Email: <input type="email" name="email"/><br/>
    Password: <input type="password" name="password"/><br/>
    <input type="submit" value="Login"/>
</form>
<c:if test="${not empty response}">
    <p>${response.status.message}</p>
</c:if>
<p><a href="signup">Signup here</a></p>
</body>
</html>
