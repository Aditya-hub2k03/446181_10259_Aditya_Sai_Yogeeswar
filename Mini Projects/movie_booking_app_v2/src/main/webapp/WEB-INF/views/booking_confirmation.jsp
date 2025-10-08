<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Booking Confirmation</title>
</head>
<body>
<c:choose>
    <c:when test="${bookingResponse.success}">
        <h2>Booking Successful!</h2>
        <p>Booking ID: ${bookingResponse.bookingId}</p>
        <p>Selected Seats: ${selectedSeats}</p>
        <p>Total Amount: ₹${bookingResponse.totalAmount}</p>
        <p>Show ID: ${showId}</p>
        <a href="${pageContext.request.contextPath}/home_loggedin">Return to Home</a>
    </c:when>
    <c:otherwise>
        <h2>Booking Failed</h2>
        <p>${bookingResponse.message}</p>
        <a href="${pageContext.request.contextPath}/home_loggedin">Return to Home</a>
    </c:otherwise>
</c:choose>
</body>
</html>
