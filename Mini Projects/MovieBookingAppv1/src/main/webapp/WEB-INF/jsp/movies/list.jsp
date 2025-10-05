<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Movies</title></head>
<body>
<h2>Movie List</h2>
<table border="1">
    <tr>
        <th>Title</th><th>Genre</th><th>Language</th><th>Release Date</th><th>Runtime (min)</th>
    </tr>
    <c:forEach var="movie" items="${movies}">
        <tr>
            <td><c:out value="${movie.title}"/></td>
            <td><c:out value="${movie.genre}"/></td>
            <td><c:out value="${movie.language}"/></td>
            <td><c:out value="${movie.releaseDate}"/></td>
            <td><c:out value="${movie.runtime}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
