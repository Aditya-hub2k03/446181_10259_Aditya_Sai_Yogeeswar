<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Shows</title></head>
<body>
<h2>Show List</h2>
<table border="1">
    <tr>
        <th>Movie ID</th><th>Theater ID</th><th>Show Time</th><th>Format</th>
    </tr>
    <c:forEach var="show" items="${shows}">
        <tr>
            <td><c:out value="${show.movieId}"/></td>
            <td><c:out value="${show.theaterId}"/></td>
            <td><c:out value="${show.showTime}"/></td>
            <td><c:out value="${show.format}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
