<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Register</title></head>
<body>
<h2>Register</h2>
<form method="post" action="${pageContext.request.contextPath}/auth/register">
    Name: <input type="text" name="name" required/><br/>
    Email: <input type="email" name="email" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
