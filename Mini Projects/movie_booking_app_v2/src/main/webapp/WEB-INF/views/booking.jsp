<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Book Seats</title>
    <style>
        .seat { width: 40px; height: 40px; margin: 5px; text-align: center; line-height: 40px; cursor: pointer; border-radius: 5px; display: inline-block; }
        .available { border: 2px solid green; }
        .selected { background-color: green; color: white; }
        .sold { border: 2px solid red; background-color: lightgray; cursor: not-allowed; }
        .best { border: 2px solid orange; }
        .handicapped { border: 2px solid green; background-color: #d1ffd1; }
    </style>
    <script>
        function toggleSeat(seatDiv) {
            if(seatDiv.classList.contains('sold') || seatDiv.classList.contains('handicapped')) return;

            seatDiv.classList.toggle('selected');
            updateSelectedSeats();
        }

        function updateSelectedSeats() {
            let selected = [];
            document.querySelectorAll('.seat.selected').forEach(function(s) {
                selected.push(s.dataset.seat);
            });
            document.getElementById('selectedSeats').value = selected.join(',');
        }
    </script>
</head>
<body>
<h2>Book Seats for Show: ${show.showTime} on ${show.showDate}</h2>
<h3>Movie: ${show.movieId} </h3>
<div>
    <c:forEach var="seat" items="${seats}">
        <div class="seat 
            <c:choose>
                <c:when test="${seat.status == 'AVAILABLE'}">available</c:when>
                <c:when test="${seat.status == 'SOLD'}">sold</c:when>
                <c:when test="${seat.status == 'BEST'}">best</c:when>
                <c:when test="${seat.status == 'HANDICAPPED'}">handicapped</c:when>
            </c:choose>"
             data-seat="${seat.seatNumber}"
             onclick="toggleSeat(this)">
            ${seat.seatNumber}
        </div>
        <c:if test="${(seatIndex % 10) == 9}"><br/></c:if>
    </c:forEach>
</div>

<form action="${pageContext.request.contextPath}/booking/confirm" method="post">
    <input type="hidden" name="showId" value="${show.showId}" />
    <input type="hidden" id="selectedSeats" name="selectedSeats" value="" />
    <button type="submit">Confirm Booking</button>
</form>
</body>
</html>
