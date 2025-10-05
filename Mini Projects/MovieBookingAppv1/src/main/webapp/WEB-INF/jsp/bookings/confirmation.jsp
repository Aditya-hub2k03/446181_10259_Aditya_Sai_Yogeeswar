<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Booking Confirmation</title></head>
<body>
<h2>Booking Confirmation</h2>

<c:if test="${not empty booking}">
    <p>Booking ID: <c:out value="${booking.bookingId}"/></p>
    <p>User ID: <c:out value="${booking.userId}"/></p>
    <p>Show ID: <c:out value="${booking.showId}"/></p>
    <p>Seat IDs: <c:out value="${booking.seatIds}"/></p>
    <p>Booking Time: <c:out value="${booking.bookingTime}"/></p>
</c:if>

<c:if test="${empty booking}">
    <p>Booking information is not available.</p>
</c:if>

</body>
</html>
