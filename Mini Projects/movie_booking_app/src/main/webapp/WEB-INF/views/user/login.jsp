<%@ include file="../common/header.jsp" %>
<h3>Login</h3>
<form action="loginUser" method="post">
    <label>Email:</label><input type="email" name="email" required><br><br>
    <label>Password:</label><input type="password" name="password" required><br><br>
    <button type="submit">Login</button>
</form>
<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>
<%@ include file="../common/footer.jsp" %>
