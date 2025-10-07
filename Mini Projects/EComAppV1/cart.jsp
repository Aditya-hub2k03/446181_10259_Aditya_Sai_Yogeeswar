<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
</head>
<body>
    <h1>Shopping Cart</h1> 
    <%
        List<String> cart = (List<String>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
    %> 
        <p>Your cart is empty.</p>
    <%
        } else {
    %>
        <ul>
        <% for (String item : cart) { %>
            <li><%= item %></li>
        <% } %>
        </ul>
    <%
        }
    %>
    <br><a href="ItemServlet">Continue Shopping</a>
</body>
</html>
