<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Book Seats</title></head>
<body>
<h2>Book Seats</h2>
<form method="post" action="${pageContext.request.contextPath}/bookings">
    User ID: <input type="number" name="userId" required/><br/>
    Show ID: <input type="number" name="showId" required/><br/>
    Seat IDs (comma separated): <input type="text" name="seatIds" required/><br/>
    <input type="submit" value="Book"/>
</form>
<p style="font-size:small; color:gray;">
    Enter seat IDs separated by commas, e.g. 1,2,3
</p>
</body>
</html>
