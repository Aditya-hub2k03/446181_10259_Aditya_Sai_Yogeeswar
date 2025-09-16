<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h2>Registration</h2>
    <form action="register" method="post">
        Username: <input type="text" name="username" required /><br/>
        Password: <input type="password" name="password" required /><br/>
        <input type="submit" value="Register" />
    </form>
    <br/>
    <a href="login">Already have an account? Login here.</a>
    <br/>
    <span style="color:red;">${error}</span>
</body>
</html>
