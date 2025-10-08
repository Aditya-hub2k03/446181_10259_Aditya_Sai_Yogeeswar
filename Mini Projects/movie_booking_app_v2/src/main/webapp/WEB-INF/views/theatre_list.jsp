<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Theatres & Shows</title>
    <style>
        .theatre-card {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
        }
        .showtime {
            display: inline-block;
            margin: 5px;
            padding: 5px 10px;
            background-color: #2e8b57;
            color: white;
            border-radius: 3px;
            cursor: pointer;
        }
        .showtime:hover {
            background-color: #246b45;
        }
    </style>
</head>
<body>
<h2>Select Theatre & Show for "${movie.title}"</h2>

<c:if test="${not empty theatreMap}">
    <c:forEach var="entry" items="${theatreMap}">
        <c:set var="theatre" value="${entry.value}" />
        <div class="theatre-card">
            <h3>${theatre.theatreName} (${theatre.city})</h3>
            <p>${theatre.address}</p>
            <div>
                <c:forEach var="show" items="${shows}">
                    <c:if test="${show.theatreId == theatre.theatreId}">
                        <a href="${pageContext.request.contextPath}/booking/show/${show.showId}" class="showtime">
                            ${show.showDate} | ${show.showTime}
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</c:if>

<c:if test="${empty theatreMap}">
    <p>No theatres available for this movie.</p>
</c:if>

</body>
</html>
