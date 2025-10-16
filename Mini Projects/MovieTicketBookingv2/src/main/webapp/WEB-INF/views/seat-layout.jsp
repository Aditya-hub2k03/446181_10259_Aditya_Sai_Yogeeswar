<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
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
        body {
            background-color: #f8f9fa;
        }
        .screen {
            background-color: #343a40;
            color: white;
            padding: 10px;
            margin: 20px auto;
            width: 80%;
            text-align: center;
            border-radius: 5px;
        }
        .seat-map {
            margin: 20px auto;
            width: 80%;
        }
        .seat-row {
            display: flex;
            justify-content: center;
            margin-bottom: 10px;
        }
        .seat {
            width: 40px;
            height: 40px;
            margin: 5px;
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            font-weight: bold;
            user-select: none;
            position: relative;
            z-index: 10;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .available {
            background-color: #28a745;
            color: white;
            cursor: pointer;
        }
        .available:hover {
            background-color: #218838;
            transform: scale(1.05);
        }
        .booked {
            background-color: #dc3545;
            color: white;
            cursor: not-allowed;
        }
        .selected {
            background-color: #007bff !important;
            color: white;
        }
        .handicapped {
            background-color: #ffc107;
            color: black;
            cursor: not-allowed;
        }
        .legend {
            display: flex;
            justify-content: center;
            margin: 20px 0;
            gap: 20px;
        }
        .legend-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .legend-color {
            width: 20px;
            height: 20px;
            border-radius: 3px;
        }
        #selectedSeats {
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 5px;
            border: 1px solid #dee2e6;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/header.jsp" />

    <div class="container mt-4">
        <h2 class="text-center mb-4">Select Seats</h2>

        <c:if test="${param.reserveSuccess != null}">
            <div class="alert alert-success">Seat reserved successfully!</div>
        </c:if>

        <div class="screen">SCREEN</div>

        <div class="text-center mb-3">
            <h5>Show ID: ${showId}</h5>
        </div>

        <div class="seat-map">
            <!-- Seat Map Layout -->
            <c:set var="rows" value="'A,B,C,D,E,F,G,H,I,J'" />
            <c:set var="rowArray" value="${fn:split(rows, ',')}" />
            <c:forEach var="row" items="${rowArray}">
                <div class="seat-row">
                    <div style="width: 40px; text-align: center; margin: 5px; font-weight: bold;">${row}</div>
                    <c:forEach var="j" begin="1" end="10">
                        <c:set var="seatNumber" value="${row}${j}" />
                        <c:set var="foundSeat" value="false" />
                        <c:forEach var="seat" items="${seats}">
                            <c:if test="${seat.seatNumber == seatNumber}">
                                <c:set var="foundSeat" value="true" />
                                <c:choose>
                                    <c:when test="${seat.available}">
                                        <div class="seat available" 
                                             data-seat-id="${seat.seatId}" 
                                             data-seat-number="${seat.seatNumber}"
                                             onclick="toggleSeatSelection(this)">
                                            ${seat.seatNumber}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="seat booked">${seat.seatNumber}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                        <c:if test="${foundSeat == false}">
                            <div class="seat" style="background-color: #e9ecef; color: #e9ecef; cursor: default;">${seatNumber}</div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>

        <!-- Legend -->
        <div class="legend">
            <div class="legend-item">
                <div class="legend-color available"></div>
                <span>Available</span>
            </div>
            <div class="legend-item">
                <div class="legend-color booked"></div>
                <span>Booked</span>
            </div>
            <div class="legend-item">
                <div class="legend-color selected"></div>
                <span>Selected</span>
            </div>
            <div class="legend-item">
                <div class="legend-color handicapped"></div>
                <span>Handicapped</span>
            </div>
        </div>

        <!-- Selected Seats Display -->
        <div id="selectedSeats" class="text-center">
            <h5>Selected Seats:</h5>
            <div id="selectedSeatsList">
                <p>No seats selected</p>
            </div>
        </div>

        <!-- Form -->
        <form action="<c:url value='/seat/reserve' />" method="post" id="reserveForm">
            <input type="hidden" name="showId" value="${showId}" />
            <input type="hidden" id="selectedSeatIds" name="seatIds" value="">
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg mt-3" id="reserveButton" disabled>
                    Reserve Selected Seats
                </button>
            </div>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let selectedSeats = [];

        function toggleSeatSelection(seatElement) {
            // Get seat data from data attributes
            const seatId = seatElement.getAttribute('data-seat-id');
            const seatNumber = seatElement.getAttribute('data-seat-number');
            
            console.log('Clicked seat:', seatNumber, 'ID:', seatId); // Debug log
            
            // Check if seat is already selected
            const index = selectedSeats.findIndex(seat => seat.id === seatId);

            if (index === -1) {
                // Add to selected seats
                selectedSeats.push({ id: seatId, number: seatNumber });
                seatElement.classList.add('selected');
                seatElement.classList.remove('available');
                console.log('Seat added:', seatNumber); // Debug log
            } else {
                // Remove from selected seats
                selectedSeats.splice(index, 1);
                seatElement.classList.remove('selected');
                seatElement.classList.add('available');
                console.log('Seat removed:', seatNumber); // Debug log
            }

            updateSelectedSeatsDisplay();
        }

        function updateSelectedSeatsDisplay() {
            const selectedSeatsList = document.getElementById('selectedSeatsList');
            const selectedSeatIdsInput = document.getElementById('selectedSeatIds');
            const reserveButton = document.getElementById('reserveButton');

            // Clear current display
            selectedSeatsList.innerHTML = '';

            if (selectedSeats.length > 0) {
                // Display selected seats as badges
                selectedSeats.forEach(seat => {
                    const seatElement = document.createElement('span');
                    seatElement.className = 'badge bg-primary me-2 mb-2';
                    seatElement.style.fontSize = '1rem';
                    seatElement.style.padding = '8px 12px';
                    seatElement.textContent = seat.number;
                    selectedSeatsList.appendChild(seatElement);
                });

                // Update hidden input with selected seat IDs (comma-separated)
                selectedSeatIdsInput.value = selectedSeats.map(seat => seat.id).join(',');

                // Enable reserve button
                reserveButton.disabled = false;
                
                console.log('Selected seat IDs:', selectedSeatIdsInput.value); // Debug log
            } else {
                selectedSeatsList.innerHTML = '<p>No seats selected</p>';
                selectedSeatIdsInput.value = '';
                reserveButton.disabled = true;
            }
        }

        // Optional: Add form validation before submit
        document.getElementById('reserveForm').addEventListener('submit', function(e) {
            if (selectedSeats.length === 0) {
                e.preventDefault();
                alert('Please select at least one seat before reserving.');
                return false;
            }
            console.log('Submitting seat IDs:', document.getElementById('selectedSeatIds').value);
        });
    </script>
</body>
</html>