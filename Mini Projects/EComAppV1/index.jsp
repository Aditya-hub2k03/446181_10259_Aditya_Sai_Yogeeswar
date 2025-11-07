<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Commerce Homepage</title>
    <style>
        body { font-family: Arial; }
        .item { border:1px solid #ccc; padding:10px; margin:10px; display:inline-block; }
        .item h3 { margin:0; }
    </style>
</head> 
<body> 
    <h1>Available Items</h1>
    <%
        List<Map<String,Object>> items = (List<Map<String,Object>>)request.getAttribute("items");
        for (Map<String,Object> item : items) {
    %>
        <div class="item">
            <h3><%= item.get("name") %></h3>
            <p>Price: ₹<%= item.get("price") %></p>
            <form action="CartServlet" method="post">
                <input type="hidden" name="name" value="<%= item.get("name") %>"/>
                <button type="submit">Add to Cart</button>
            </form>
        </div>
    <%
        }
    %>
    <br><a href="cart.jsp">View Cart</a>
</body>
</html>
