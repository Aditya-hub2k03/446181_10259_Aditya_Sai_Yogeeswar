<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Signup</title></head>
<body>
<h2>Signup</h2>
<form action="signup" method="post">
    Name: <input type="text" name="name"/><br/>
    Email: <input type="email" name="email"/><br/>
    Password: <input type="password" name="password"/><br/>
    <input type="submit" value="Signup"/>
</form>
<c:if test="${not empty response}">
    <p>${response.status.message}</p>
</c:if>
<p><a href="login">Login here</a></p>
</body>
</html>
