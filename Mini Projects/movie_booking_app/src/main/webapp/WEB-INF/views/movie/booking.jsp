<%@ include file="../common/header.jsp" %>
<h3>Seat Selection for Show #${showId}</h3>

<form action="confirmBooking" method="post">
    <input type="hidden" name="showId" value="${showId}">
    <label>Total Amount: ₹</label><input type="number" name="amount" required><br><br>
    <button type="submit">Proceed to Payment</button>
</form>

<%@ include file="../common/footer.jsp" %>
