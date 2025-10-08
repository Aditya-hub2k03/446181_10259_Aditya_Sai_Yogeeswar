<%@ include file="../common/header.jsp" %>
<h3>Payment Gateway</h3>
<form action="processPayment" method="post">
    <input type="hidden" name="bookingId" value="${bookingId}">
    <label>Amount: ₹${amount}</label><br><br>
    <select name="method" required>
        <option value="CREDIT_CARD">Credit Card</option>
        <option value="DEBIT_CARD">Debit Card</option>
        <option value="UPI">UPI</option>
        <option value="NET_BANKING">Net Banking</option>
    </select><br><br>
    <button type="submit">Pay Now</button>
</form>
<%@ include file="../common/footer.jsp" %>
