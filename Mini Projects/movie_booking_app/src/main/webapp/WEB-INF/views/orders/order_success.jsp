<%@ include file="../common/header.jsp" %>
<h3>✅ Payment Successful!</h3>
<p>Your booking is confirmed.</p>
<p>Booking ID: ${booking.bookingId}</p>
<p>Show ID: ${booking.showId}</p>
<p>Status: ${booking.status}</p>
<p>Amount: ₹${booking.totalAmount}</p>
<a href="home">Return to Homepage</a>
<%@ include file="../common/footer.jsp" %>
