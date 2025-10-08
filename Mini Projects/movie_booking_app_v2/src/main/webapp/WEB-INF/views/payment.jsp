<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Payment</title>
    <style>
        .payment-container { width: 400px; margin: 50px auto; }
        .payment-container h2 { text-align: center; }
        .pay-button {
            background-color: #2e8b57;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            margin-top: 20px;
        }
        .pay-button:hover { background-color: #246b45; }
    </style>
</head>
<body>
<div class="payment-container">
    <h2>Payment for Booking #${booking.bookingId}</h2>
    <p>Total Amount: $${booking.totalAmount}</p>
    <form action="${pageContext.request.contextPath}/booking/confirm" method="post">
        <!-- Payment options (mock) -->
        <label><input type="radio" name="paymentMethod" value="CARD" checked> Credit/Debit Card</label><br/>
        <label><input type="radio" name="paymentMethod" value="UPI"> UPI</label><br/>
        <label><input type="radio" name="paymentMethod" value="WALLET"> Wallet</label><br/>
        <input type="hidden" name="showId" value="${booking.showId}"/>
        <input type="hidden" name="selectedSeats" value="${booking.selectedSeats}"/>
        <button class="pay-button" type="submit">Pay Now</button>
    </form>
</div>
</body>
</html>
