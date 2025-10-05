<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Movie Details</title></head>
<body>
<h2>Movie Details</h2>

<c:if test="${not empty movie}">
    <p>Title: <c:out value="${movie.title}"/></p>
    <p>Genre: <c:out value="${movie.genre}"/></p>
    <p>Language: <c:out value="${movie.language}"/></p>
    <p>Release Date: <c:out value="${movie.releaseDate}"/></p>
    <p>Runtime: <c:out value="${movie.runtime}"/> minutes</p>
    <img src="<c:out value='${movie.posterUrl}'/>" alt="Movie Poster" height="300"/>
</c:if>

<c:if test="${empty movie}">
    <p>Movie details not available.</p>
</c:if>

</body>
</html>
