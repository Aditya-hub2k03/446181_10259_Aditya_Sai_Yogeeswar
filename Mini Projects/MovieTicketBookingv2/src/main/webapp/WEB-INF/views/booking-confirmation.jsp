<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - Confirmation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2>Booking Confirmation</h2>

        <c:if test="${status == 'CONFIRMED'}">
            <div class="alert alert-success">Your booking has been confirmed!</div>
        </c:if>
        <c:if test="${status == 'FAILED'}">
            <div class="alert alert-danger">Your booking has failed. Please try again.</div>
        </c:if>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Booking ID: ${booking.bookingId}</h5>
                <p class="card-text"><strong>Total Price:</strong> ${booking.totalPrice}</p>
                <p class="card-text"><strong>Status:</strong> ${status}</p>
                <a href="/" class="btn btn-primary">Back to Home</a>
            </div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
