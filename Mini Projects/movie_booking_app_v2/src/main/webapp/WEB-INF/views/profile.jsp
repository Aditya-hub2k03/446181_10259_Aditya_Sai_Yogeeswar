<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<h1>${user.userName}'s Profile</h1>
<p>Email: ${user.email}</p>
<p>Role: ${user.role}</p>

<h2>Previous Bookings</h2>
<table border="1">
    <tr>
        <th>Booking ID</th>
        <th>Show ID</th>
        <th>Seats</th>
        <th>Total Amount</th>
        <th>Status</th>
        <th>Booking Date</th>
    </tr>
    <c:forEach var="b" items="${bookings}">
        <tr>
            <td>${b.bookingId}</td>
            <td>${b.showId}</td>
            <td>${b.seatCount}</td>
            <td>${b.totalAmount}</td>
            <td>${b.status}</td>
            <td>${b.bookingDate}</td>
        </tr>
    </c:forEach>
</table>

<a href="home_loggedin">Back to Home</a>
</body>
</html>
