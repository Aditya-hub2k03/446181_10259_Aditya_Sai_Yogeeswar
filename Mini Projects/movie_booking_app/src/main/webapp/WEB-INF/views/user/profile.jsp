<%@ include file="../common/header.jsp" %>
<h3>Welcome, ${user.userName}</h3>

<p>Email: ${user.email}</p>
<p>City: ${user.city}</p>

<a href="editProfile">Edit Profile</a> | 
<a href="bookings">View Bookings</a> | 
<a href="logout">Logout</a>

<hr>
<h4>Your Bookings</h4>
<c:forEach var="b" items="${bookings}">
    <p>Booking ID: ${b.bookingId} | Status: ${b.status} | Amount: ₹${b.totalAmount}</p>
</c:forEach>

<%@ include file="../common/footer.jsp" %>
