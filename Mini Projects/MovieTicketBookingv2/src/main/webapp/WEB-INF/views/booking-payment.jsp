<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2>Payment</h2>

        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Booking ID: ${booking.bookingId}</h5>
                <p class="card-text"><strong>Total Price:</strong> ${booking.totalPrice}</p>
                <p class="card-text"><strong>Status:</strong> ${booking.status}</p>
            </div>
        </div>

        <form action="<c:url value='/payment/process' />" method="post">
            <input type="hidden" name="bookingId" value="${booking.bookingId}" />
            <input type="hidden" name="userId" value="${sessionScope.userId}" />
            <input type="hidden" name="amount" value="${booking.totalPrice}" />

            <div class="mb-3">
                <label for="paymentMethod" class="form-label">Payment Method</label>
                <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                    <option value="CREDIT_CARD">Credit Card</option>
                    <option value="DEBIT_CARD">Debit Card</option>
                    <option value="UPI">UPI</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="transactionId" class="form-label">Transaction ID</label>
                <input type="text" class="form-control" id="transactionId" name="transactionId" required>
            </div>
            <button type="submit" class="btn btn-primary">Confirm Payment</button>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
