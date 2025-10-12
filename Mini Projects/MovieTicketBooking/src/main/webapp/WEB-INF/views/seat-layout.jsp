<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Ticket Booking - Seat Layout</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .seat {
            width: 40px;
            height: 40px;
            margin: 5px;
            display: inline-block;
            text-align: center;
            line-height: 40px;
            border-radius: 5px;
            cursor: pointer;
        }
        .available { background-color: #28a745; color: white; }
        .booked { background-color: #dc3545; color: white; }
        .selected { background-color: #007bff; color: white; }
        .handicapped { background-color: #ffc107; color: black; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2>Select Seats</h2>

        <c:if test="${param.reserveSuccess != null}">
            <div class="alert alert-success">Seat reserved successfully!</div>
        </c:if>

        <div class="screen text-center mb-4">
            <div class="bg-dark text-white p-2">SCREEN</div>
        </div>

        <form action="<c:url value='/seat/reserve' />" method="post">
            <input type="hidden" name="showId" value="${showId}" />

            <div class="seats text-center">
                <c:forEach var="seat" items="${seats}">
                    <c:choose>
                        <c:when test="${seat.available}">
                            <div class="seat available" onclick="selectSeat(${seat.seatId})">${seat.seatNumber}</div>
                        </c:when>
                        <c:when test="${seat.handicapped}">
                            <div class="seat handicapped">${seat.seatNumber}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="seat booked">${seat.seatNumber}</div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <input type="hidden" id="selectedSeatId" name="seatId" required>
            <button type="submit" class="btn btn-primary mt-3">Reserve Selected Seat</button>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function selectSeat(seatId) {
            document.querySelectorAll('.seat').forEach(seat => {
                seat.classList.remove('selected');
            });
            event.target.classList.add('selected');
            document.getElementById('selectedSeatId').value = seatId;
        }
    </script>
</body>
</html>
