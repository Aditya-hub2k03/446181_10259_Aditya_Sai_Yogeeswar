<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2>Profile</h2>

        <c:if test="${param.success != null}">
            <div class="alert alert-success">Profile updated successfully!</div>
        </c:if>
        <c:if test="${param.paymentSuccess != null}">
            <div class="alert alert-success">Payment method added successfully!</div>
        </c:if>

        <form action="/user/update-profile" method="post" class="mt-4">
            <input type="hidden" name="userId" value="${user.userId}" />
            <div class="mb-3">
                <label for="userName" class="form-label">Name</label>
                <input type="text" class="form-control" id="userName" name="userName" value="${user.userName}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
            <div class="mb-3">
                <label for="city" class="form-label">City</label>
                <input type="text" class="form-control" id="city" name="city" value="${user.city}" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>

        <h3 class="mt-4">Add Payment Method</h3>
        <form action="/user/add-payment-method" method="post" class="mt-3">
            <div class="mb-3">
                <label for="cardNumber" class="form-label">Card Number</label>
                <input type="text" class="form-control" id="cardNumber" name="cardNumber" required>
            </div>
            <div class="mb-3">
                <label for="cardHolder" class="form-label">Card Holder</label>
                <input type="text" class="form-control" id="cardHolder" name="cardHolder" required>
            </div>
            <div class="mb-3">
                <label for="expiryDate" class="form-label">Expiry Date</label>
                <input type="text" class="form-control" id="expiryDate" name="expiryDate" placeholder="MM/YY" required>
            </div>
            <div class="mb-3">
                <label for="paymentType" class="form-label">Payment Type</label>
                <select class="form-select" id="paymentType" name="paymentType" required>
                    <option value="CREDIT_CARD">Credit Card</option>
                    <option value="DEBIT_CARD">Debit Card</option>
                    <option value="UPI">UPI</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Add Payment Method</button>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
