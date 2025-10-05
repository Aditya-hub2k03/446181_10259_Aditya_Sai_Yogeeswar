<html>
<head><title>Make Payment</title></head>
<body>
<h2>Make Payment</h2>
<form method="post" action="${pageContext.request.contextPath}/payments">
    Booking ID: <input type="number" name="bookingId" required/><br/>
    Amount: <input type="number" step="0.01" name="amount" required/><br/>
    Payment Method: <input type="text" name="paymentMethod" required/><br/>
    <input type="submit" value="Pay"/>
</form>
</body>
</html>
